package se.hactar.movieregister.helper.imdb.model


import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Result {
    @SerializedName("l")
    var _title: String? = null
    val title: String
        get() = _title ?: ""

    @SerializedName("id")
    private var _id: String? = null
    val id: String
        get() = _id ?: ""

    @SerializedName("s")
    private var _starring: String? = null
    val starring: String
        get() = _starring ?: ""

    @SerializedName("y")
    var year: Int = 0

    @SerializedName("q")
    private var _type: String? = null
    val type: String
        get() = _type ?: ""

    @SerializedName("i")
    private var image: List<String> = ArrayList()

    val imageUrl: String
        get() = if (!image.isEmpty()) image[0] else ""

    override fun toString(): String {
        return "Result{" +
                "title='" + title + '\''.toString() +
                ", id='" + id + '\''.toString() +
                ", starring='" + starring + '\''.toString() +
                ", year=" + year +
                ", type='" + type + '\''.toString() +
                ", imageUrl=" + imageUrl +
                '}'.toString()
    }
}
