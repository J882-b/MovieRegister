package se.hactar.movieregister.helper.imdb.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public final class Result {
    @SerializedName("l")
    public String title;

    @SerializedName("id")
    public String id;

    @SerializedName("s")
    public String starring;

    @SerializedName("y")
    public int year;

    @SerializedName("q")
    public String type;

    @SerializedName("i")
    public final List<String> image = new ArrayList<>();

    public String getTitle() {
        return title == null ? "" : title;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public String getStarring() {
        return starring == null ? "" : starring;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public String getImageUrl() {
        if (!image.isEmpty()) {
            return image.get(0);
        }
        return "";
    }

    @Override
    public String toString() {
        return "Result{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", starring='" + starring + '\'' +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", imageUrl=" + getImageUrl() +
                '}';
    }
}
