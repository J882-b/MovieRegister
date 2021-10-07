package se.hactar.movieregister.api.imdb.model


import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Result {
    @SerializedName("l")
    var title: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("s")
    var starring: String? = null

    @SerializedName("y")
    var year: Int = 0

    @SerializedName("q")
    var type: String? = null

    @SerializedName("i")
    var images: List<String> = ArrayList()

    override fun toString(): String {
        return "Result(title=$title, id=$id, starring=$starring, year=$year, type=$type, images=$images)"
    }
}
