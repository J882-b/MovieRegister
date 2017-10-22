package se.hactar.movieregister.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Result {
    @SerializedName("l")
    public String title;

    @SerializedName("id")
    public String id;

    @SerializedName("s")
    public String starring;

    @SerializedName("y")
    public String year;

    @SerializedName("q")
    public String type;

    @SerializedName("i")
    public final List<String> image = new ArrayList<>();

    @Override
    public String toString() {
        return "Result{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", starring='" + starring + '\'' +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", image=" + image +
                '}';
    }
}
