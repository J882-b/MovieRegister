package se.hactar.movieregister.repository


import android.os.AsyncTask
import android.text.TextUtils
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import se.hactar.movieregister.MovieApp
import se.hactar.movieregister.R
import se.hactar.movieregister.helper.GsonPConverterFactory
import se.hactar.movieregister.helper.ImportMovie
import se.hactar.movieregister.helper.imdb.ImdbHelper
import se.hactar.movieregister.helper.imdb.model.Suggest
import se.hactar.movieregister.model.Movie
import timber.log.Timber
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

object MovieRepository  {
    private val retrofit = Retrofit.Builder()
            .baseUrl(ImdbHelper.BASE_URL)
            .addConverterFactory(GsonPConverterFactory(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    private val imdb = retrofit.create(ImdbHelper.Api::class.java)
    private val movieDao = MovieApp.db.movieDao()

    fun importMovies() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute {
            val movies = ArrayList<Movie>()

            val resources = MovieApp.app.resources
            try {
                resources.openRawResource(R.raw.filmregister).use { inputStream ->
                    InputStreamReader(inputStream).use { inputStreamReader ->
                        BufferedReader(inputStreamReader).use { reader ->

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
                                val movie = ImportMovie.parse(inputLine)
                                movies.add(movie)
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                Timber.e("Prolem importing movies from file.")
            }

            movieDao.insertAll(movies)
            downloadPosterUrls()
        }
    }

    private fun downloadPosterUrls() {
        Observable.fromIterable(movieDao.all)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter { movie -> !TextUtils.isEmpty(movie.imdbId) }
                .concatMap { movie -> imdb.getSuggest(firstLetter(movie.imdbId!!), movie.imdbId!!) }
                .map { this.createIdUrlEntry(it) }
                .subscribe({ this.setPosterUrlInDb(it) },  { Timber.e(it) })
    }

    private fun createIdUrlEntry(suggest: Suggest): Pair<String, String> {
        val result = suggest.firstResult
        return Pair(result.id, result.imageUrl)
    }

    private fun setPosterUrlInDb(pair: Pair<String, String>) {
        val movie = movieDao.get(pair.first)
        movie.posterUrl = pair.second
        movieDao.update(movie)
    }

    private fun firstLetter(string: String): String {
        return string.substring(0, 1)
    }
}
