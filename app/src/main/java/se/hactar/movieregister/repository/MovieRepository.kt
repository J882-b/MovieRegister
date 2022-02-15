package se.hactar.movieregister.repository


import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import se.hactar.movieregister.MovieApp
import se.hactar.movieregister.api.imdb.Imdb
import se.hactar.movieregister.model.Movie
import java.io.InputStream

object MovieRepository {
    private const val FIRST_LINE_PATTERN = "HMRHMRHMRHMRHMR"
    private val TAG = MovieRepository::class.java.simpleName
    private val movieDao = MovieApp.database.movieDao()

    // TODO: Redesign import movies.
    fun importMovies(inputStream: InputStream) {
        // TODO: Find new solution without GlobalScope here.
        GlobalScope.launch {

            // TODO: Unit tests.
            val movies = inputStream.bufferedReader()
                    .lineSequence()
                    // TODO: Verify first line
                    .dropWhile { it == FIRST_LINE_PATTERN }
                    .map { Movie.from(it) }
                    .filterNotNull()
                    .onEach { Log.d(TAG, "Parsed movie: $it") }
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
                .map { Imdb.getSuggest(it!!) }
                .map { it.results.first() }
                .collect { setPosterUrlInDb(it.id!!, it.images.first()) }
    }

    private fun setPosterUrlInDb(imdbId: String, posterUrl: String) {
        val movie = movieDao[imdbId]
        Log.d(TAG, "Setting poster url: $posterUrl for $imdbId")
        movie.posterUrl = posterUrl
        movieDao.update(movie)
    }

    fun clearMovies() {
        GlobalScope.launch {
            movieDao.deleteAll()
        }
    }
}
