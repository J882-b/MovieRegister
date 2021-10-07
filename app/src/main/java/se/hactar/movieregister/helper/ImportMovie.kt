package se.hactar.movieregister.helper

import android.util.Log
import se.hactar.movieregister.model.Movie

object ImportMovie {

    private val TAG = ImportMovie::class.java.simpleName

    fun parse(line: String): Movie {
        // Example: A|1|tt0107048|DVD|Groundhog Day|1993
        if (!line.matches(".*\\.*\\|.*\\|.*\\|.*\\|.*".toRegex())) {
            Log.i(TAG, "Ignoring line that does not match import pattern, line=$line")
        }
        val columns = line.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val movie = Movie().apply {
            container = get(columns, Columns.CONTAINER)
            index = get(columns, Columns.INDEX)
            imdbId = get(columns, Columns.IMDB_ID)
            type = get(columns, Columns.TYPE)
            name = get(columns, Columns.NAME)
            year = get(columns, Columns.YEAR)
        }
        return movie
    }

    private operator fun get(columns: Array<String>, column: Columns): String {
        return if (column.ordinal < columns.size) columns[column.ordinal] else ""
    }

    private enum class Columns {
        CONTAINER, INDEX, IMDB_ID, TYPE, NAME, YEAR
    }
}
