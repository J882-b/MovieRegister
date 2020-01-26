package se.hactar.movieregister

import android.app.Application
import androidx.room.Room

import se.hactar.movieregister.model.MovieDatabase
import timber.log.Timber

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, MovieDatabase::class.java, "movie_db").build()

        // TODO: disable logging for release build
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var database: MovieDatabase
            private set
    }
}
