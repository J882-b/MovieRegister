package se.hactar.movieregister.repository.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.repository.dto.Result;

@SuppressWarnings("unused")
public class Suggest {

    @SerializedName("v")
    public int v;

    @SerializedName("q")
    public String query;

    @SerializedName("d")
    public final List<Result> results = new ArrayList<>();

    @Override
    public String toString() {
        return "Suggest{" +
                "v=" + v +
                ", query='" + query + '\'' +
                ", results=" + results +
                '}';
    }
}
