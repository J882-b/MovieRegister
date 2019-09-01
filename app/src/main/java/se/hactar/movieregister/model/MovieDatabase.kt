package se.hactar.movieregister.model


import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
