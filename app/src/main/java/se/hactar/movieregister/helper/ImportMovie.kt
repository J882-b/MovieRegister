package se.hactar.movieregister.helper

import android.util.Log
import se.hactar.movieregister.model.Movie

object ImportMovie {

    private val TAG = ImportMovie::class.java.simpleName

    // TODO: Move to Movie.from()
    fun parse(line: String): Movie {
        // Example: A|1|tt0107048|DVD|Groundhog Day|1993|
        // Example: A|13|tt0080274|DVD|Shogun |1980|Disc 1
        if (!line.matches(".*\\|.*\\|.*\\|.*\\|.*\\|.*\\|.*".toRegex())) {
            Log.i(TAG, "Ignoring line that does not match import pattern, line=$line")
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
