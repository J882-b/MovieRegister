package se.hactar.movieregister.data;


import android.support.annotation.NonNull;

public class Movie {
    private final String index;
    private final String name;
    private final String year;
    private final String type;
    private final String id;

    public static Movie parse(final String line) {
        if (!line.matches(".*¤.*¤.*¤.*¤.*")) {
            throw new IllegalArgumentException("Movie line could not be split into four rows. line=" + line);
        }
        String[] row = line.split("¤");
        if (row.length == 4) {
            return new Movie(row[0], row[1], row[2], row[3], null);
        }
        return new Movie(row[0], row[1], row[2], row[3], row[4]);
    }

    private Movie(final String index, final String name, final String year, final String type, final String id) {
        this.index = index;
        this.name = name;
        this.year = year;
        this.type = type;
        this.id = id;
    }

    @NonNull
    public String getIndex() {
        return index == null ? "" : index;
    }

    @NonNull
    public String getName() {
        return name == null ? "" : name;
    }

    @NonNull
    public String getType() {
        return type == null ? "" : type;
    }

    /**
     * @return the IMDB id of this movie
     */
    @NonNull
    public String getId() {
        return id == null ? "" : id;
    }
}