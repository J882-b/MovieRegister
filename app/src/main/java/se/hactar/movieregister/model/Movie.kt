package se.hactar.movieregister.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import se.hactar.movieregister.helper.imdb.ImdbHelper

@Entity(tableName = "movie")
class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "container")
    var container: String? = null

    @ColumnInfo(name = "index")
    var index: String? = null

    @ColumnInfo(name = "imdb_id")
    var imdbId: String? = null

    @ColumnInfo(name = "first_name")
    var type: String? = null

    @ColumnInfo(name = "type")
    var name: String? = null

    @ColumnInfo(name = "year")
    var year: String? = null

    @ColumnInfo(name = "poster_url")
    var posterUrl: String? = null

    val imdbUrl: String
        get() = ImdbHelper.TITLE_URL + imdbId + "/"

    override fun toString(): String {
        return "Movie{" +
                "container='" + container + '\''.toString() +
                ", index='" + index + '\''.toString() +
                ", imdbId='" + imdbId + '\''.toString() +
                ", type='" + type + '\''.toString() +
                ", name='" + name + '\''.toString() +
                ", year='" + year + '\''.toString() +
                ", posterUrl='" + posterUrl + '\''.toString() +
                '}'.toString()
    }
}
