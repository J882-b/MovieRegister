package se.hactar.movieregister.data;

public class Movie {
    //------------------------------------------------------------ class (static)
    public static Movie parse(String line) {
        if (!line.matches(".*¤.*¤.*¤.*¤.*")) {
            throw new IllegalArgumentException("Movie line could not be split into four rows. line=" + line);
        }
        String[] row = line.split("¤");
        if (row.length == 4) {
            return new Movie(row[0], row[1], row[2], row[3], null);
        }
        return new Movie(row[0], row[1], row[2], row[3], row[4]);
    }

    //------------------------------------------------------------ object (not static)
    private String index;
    private String name;
    private String year;
    private String type;
    private String id;

    public Movie(String index, String name, String year, String type, String id) {
        this.index = index;
        this.name = name;
        this.year = year;
        this.type = type;
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    /**
     * @return the IMDB id of this movie
     */
    public String getId() {
        return id;
    }
}