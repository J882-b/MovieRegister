package se.hactar.movieregister.db;

import timber.log.Timber;

public class Movie {
    private String container;
    private String index;
    private String imdbId;
    private String type;
    private String name;
    private String year;
    private String posterUrl;

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

    private static String get(final String[] columns, final Movie.Columns column) {
        return column.ordinal() < columns.length ? columns[column.ordinal()] : "";
    }

    private enum Columns {CONTAINER, INDEX, IMDB_ID, TYPE, NAME, YEAR}

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "container='" + container + '\'' +
                ", index='" + index + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
