package se.hactar.movieregister.helper;

import se.hactar.movieregister.model.Movie;
import timber.log.Timber;

public class ImportMovie {

    public static Movie parse(final String line) {
        // Example: Film 1¤4¤tt0372588¤DVD¤Team America: World Police¤2004
        if (!line.matches(".*¤.*¤.*¤.*¤.*¤.*")) {
            Timber.i("Ignoring line that does not match import pattern, line=" + line);
        }
        final String[] columns = line.split("¤");
        Movie movie = new Movie();
        movie.setContainer(get(columns, Columns.CONTAINER));
        movie.setIndex(get(columns, Columns.INDEX));
        movie.setImdbId(get(columns, Columns.IMDB_ID));
        movie.setType(get(columns, Columns.TYPE));
        movie.setName(get(columns, Columns.NAME));
        movie.setYear(get(columns, Columns.YEAR));
        return movie;
    }

    private static String get(final String[] columns, final ImportMovie.Columns column) {
        return column.ordinal() < columns.length ? columns[column.ordinal()] : "";
    }

    private enum Columns {CONTAINER, INDEX, IMDB_ID, TYPE, NAME, YEAR}
}
