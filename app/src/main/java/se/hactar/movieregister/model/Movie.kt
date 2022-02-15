package se.hactar.movieregister.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

    @ColumnInfo(name = "disc_type")
    var discType: String? = null

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "year")
    var year: String? = null

    @ColumnInfo(name = "index_title")
    var indexTitle: String? = null

    @ColumnInfo(name = "poster_url")
    var posterUrl: String? = null

    override fun toString(): String {
        return "Movie(id=$id, container=$container, index=$index, imdbId=$imdbId, " +
                "discType=$discType, title=$title, year=$year, indexTitle=$indexTitle, " +
                "posterUrl=$posterUrl)"
    }
}
