package se.hactar.movieregister.repository


import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import se.hactar.movieregister.MovieApp
import se.hactar.movieregister.helper.GsonPConverterFactory
import se.hactar.movieregister.helper.ImportMovie
import se.hactar.movieregister.helper.imdb.ImdbHelper
import timber.log.Timber
import java.io.InputStream

object MovieRepository {
    private const val FIRST_LINE_PATTERN = "HMRHMRHMRHMRHMR"
    private val retrofit = Retrofit.Builder()
            .baseUrl(ImdbHelper.BASE_URL)
            .addConverterFactory(GsonPConverterFactory(Gson()))
            .build()
    private val imdb = retrofit.create(ImdbHelper.Api::class.java)
    private val movieDao = MovieApp.database.movieDao()

    fun importMovies(inputStream: InputStream) {
        GlobalScope.launch {

            val movies = inputStream.bufferedReader()
                    .lineSequence()
                    // TODO: Verify first line
                    .dropWhile { it == FIRST_LINE_PATTERN }
                    .map { ImportMovie.parse(it) }
                    .onEach { Timber.d("Parsed movie: $it") }
                    .toList()

            movieDao.insertAll(movies)
            downloadPosterUrls()
        }
    }

    private suspend fun downloadPosterUrls() {
        movieDao.all
                .filterNot { it.imdbId.isNullOrEmpty() }
                .map { it.imdbId }
                .asFlow()
                .map { imdb.getSuggest(firstLetter(it!!), it) }
                .map { it.results.first() }
                .collect { setPosterUrlInDb(it.id!!, it.images.first()) }
    }

    private fun setPosterUrlInDb(imdbId: String, posterUrl: String) {
        val movie = movieDao[imdbId]
        Timber.d("Setting poster url: $posterUrl for $imdbId")
        movie.posterUrl = posterUrl
        movieDao.update(movie)
    }

    private fun firstLetter(string: String): String {
        return string.substring(0, 1)
    }

    fun clearMovies() {
        GlobalScope.launch {
            movieDao.deleteAll()
        }
    }
}
