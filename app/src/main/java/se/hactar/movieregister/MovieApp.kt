package se.hactar.movieregister

import android.app.Application
import android.arch.persistence.room.Room

import se.hactar.movieregister.model.MovieDatabase
import timber.log.Timber

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        db = Room.databaseBuilder(this, MovieDatabase::class.java, "movie_db").build()

        // TODO: disable logging for release build
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var app: MovieApp
        lateinit var db: MovieDatabase
    }
}
