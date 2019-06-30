package se.hactar.movieregister.helper

import se.hactar.movieregister.model.Movie
import timber.log.Timber

object ImportMovie {

    fun parse(line: String): Movie {
        // Example: Film 1¤4¤tt0372588¤DVD¤Team America: World Police¤2004
        if (!line.matches(".*¤.*¤.*¤.*¤.*¤.*".toRegex())) {
            Timber.i("Ignoring line that does not match import pattern, line=" + line)
        }
        val columns = line.split("¤".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val movie = Movie()
        movie.container = get(columns, Columns.CONTAINER)
        movie.index = get(columns, Columns.INDEX)
        movie.imdbId = get(columns, Columns.IMDB_ID)
        movie.type = get(columns, Columns.TYPE)
        movie.name = get(columns, Columns.NAME)
        movie.year = get(columns, Columns.YEAR)
        return movie
    }

    private operator fun get(columns: Array<String>, column: ImportMovie.Columns): String {
        return if (column.ordinal < columns.size) columns[column.ordinal] else ""
    }

    private enum class Columns {
        CONTAINER, INDEX, IMDB_ID, TYPE, NAME, YEAR
    }
}
