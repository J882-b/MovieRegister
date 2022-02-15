package se.hactar.movieregister.model

import android.util.Log
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

    companion object {
        private val TAG = Movie::class.java.simpleName

        fun from(line: String): Movie? {
            // Example: A|1|tt0107048|DVD|Groundhog Day|1993|
            // Example: A|12|tt0080274|DVD|Shogun |1980|Disc 1
            if (!line.matches(".+\\|.+\\|.+\\|.+\\|.+\\|.+\\|.*".toRegex())) {
                Log.i(TAG, "Ignoring line that does not match import pattern, line=$line")
                return null
            }
            val columns = line.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val movie = Movie().apply {
                container = get(columns, Columns.CONTAINER)
                index = get(columns, Columns.INDEX)
                imdbId = get(columns, Columns.IMDB_ID)
                discType = get(columns, Columns.DISC_TYPE)
                title = get(columns, Columns.TITLE)
                year = get(columns, Columns.YEAR)
                indexTitle = get(columns, Columns.INDEX_TITLE)
            }
            return movie
        }

        private operator fun get(columns: Array<String>, column: Columns): String {
            return if (column.ordinal < columns.size) columns[column.ordinal] else ""
        }

        private enum class Columns {
            CONTAINER, INDEX, IMDB_ID, DISC_TYPE, TITLE, YEAR, INDEX_TITLE
        }
    }
}
