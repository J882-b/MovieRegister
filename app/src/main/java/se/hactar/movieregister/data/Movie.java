package se.hactar.movieregister.data;

public class Movie {
    //------------------------------------------------------------ class (static)
    public static Movie parse(String line) {
        //fun parse (line:String):Movie {
        String[] row = line.split("¤");
        if (row.length == 4) {
            throw new IllegalArgumentException("Movie line could not be split into four rows.");
        }
        return new Movie(row[0], row[1], row[2], row[3]);
    }

    //------------------------------------------------------------ object (not static)
    private String index;
    private String name;
    private String type;
    private String id;

    public Movie(String index, String name, String type, String id) {
        this.index = index;
        this.name = name;
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