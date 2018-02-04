package se.hactar.movieregister.model


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie")
    val allLiveData: LiveData<List<Movie>>

    @get:Query("SELECT * FROM movie")
    val all: List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movie WHERE imdb_id=:arg0")
    operator fun get(imdbId: String): Movie

    @Update
    fun update(movie: Movie)
}
