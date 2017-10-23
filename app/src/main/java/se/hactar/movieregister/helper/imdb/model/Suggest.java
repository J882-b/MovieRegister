package se.hactar.movieregister.helper.imdb.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class Suggest {

    @SerializedName("q")
    private String query;

    @SerializedName("d")
    private final List<Result> results = new ArrayList<>();

    public String getQuery() {
        return query == null ? "" : query;
    }

    public Result getFirstResult() {
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return new Result();
    }

    public List<Result> getResults() {
        return results == null ? new ArrayList<>(): results;
    }

    @Override
    public String toString() {
        return "Suggest{" +
                "query='" + query + '\'' +
                ", results=" + results +
                '}';
    }
}
