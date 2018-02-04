package se.hactar.movieregister.helper.imdb.model


import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Suggest {

    @SerializedName("q")
    private val query: String? = null
        get() = field ?: ""

    @SerializedName("d")
    private val results = ArrayList<Result>()
        get() = field ?: ArrayList()

    val firstResult: Result
        get() = if (!results.isEmpty()) results[0] else Result()

    override fun toString(): String {
        return "Suggest{" +
                "query='" + query + '\''.toString() +
                ", results=" + results +
                '}'.toString()
    }
}
