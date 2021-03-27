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
import se.hactar.movieregister.model.Movie
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object MovieRepository {
    private val retrofit = Retrofit.Builder()
            .baseUrl(ImdbHelper.BASE_URL)
            .addConverterFactory(GsonPConverterFactory(Gson()))
            .build()
    private val imdb = retrofit.create(ImdbHelper.Api::class.java)
    private val movieDao = MovieApp.database.movieDao()

    fun importMovies(inputStream: InputStream) {
        GlobalScope.launch {
            val movies = ArrayList<Movie>()

            try {
                InputStreamReader(inputStream).use { inputStreamReader ->
                    BufferedReader(inputStreamReader).use { reader ->

                        var first = true
                        var inputLine: String?
                        while (true) {
                            try {
                                inputLine = reader.readLine()
                            } catch (e: IOException) {
                                Timber.e(e, "Error while reading a line from file")
                                break
                            }

                            if (inputLine == null) {
                                // Exit loop if no lines left
                                break
                            }

                            if (first) {
                                first = false
                                // Check HMR signature to know if this is a Movie Register file
                                if (inputLine != "HMRHMRHMRHMRHMR") {
                                    break
                                }
                                continue
                            }

                            val movie = ImportMovie.parse(inputLine)
                            Timber.d("Adding movie: $movie")
                            movies.add(movie)
                        }
                    }
                }
            } catch (e: IOException) {
                Timber.e("Problem importing movies from file.")
            }

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
