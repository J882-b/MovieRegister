package se.hactar.movieregister

import android.app.Application
import androidx.room.Room

import se.hactar.movieregister.model.MovieDatabase

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, MovieDatabase::class.java, "movie_db").build()
    }

    companion object {
        lateinit var database: MovieDatabase
            private set
    }
}
