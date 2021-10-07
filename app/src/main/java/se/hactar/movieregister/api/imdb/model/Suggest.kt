package se.hactar.movieregister.api.imdb.model


import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Suggest {

    @SerializedName("q")
    private var query: String? = null

    @SerializedName("d")
    var results = ArrayList<Result>()

    override fun toString(): String {
        return "Suggest(query=$query, results=$results)"
    }
}
