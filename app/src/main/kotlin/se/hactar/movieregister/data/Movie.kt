package se.hactar.movieregister.data

/**
 * Created by dilbert on 2015-10-14.
 */
public data class Movie(val index: String, val name: String, val year: String, val type: String, val imdbId: String) {
    object Factory {
        fun parse(line: String) : Movie {
            val row = line.split("¤")
            if (row.size() != 5) {
                throw IllegalArgumentException("Movie line could not be split into four rows.")
            }
            return Movie(row[0], row[1], row[2], row[3], row[4])
        }
    }
}