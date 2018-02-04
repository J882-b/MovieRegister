package se.hactar.movieregister.helper.imdb.model


import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class Result {
    @SerializedName("l")
    var title: String? = null
        get() = field ?: ""

    @SerializedName("id")
    var id: String? = null
        get() = field ?: ""

    @SerializedName("s")
    var starring: String? = null
        get() = field ?: ""

    @SerializedName("y")
    var year: Int = 0

    @SerializedName("q")
    var type: String? = null
        get() = field ?: ""

    @SerializedName("i")
    val image: List<String> = ArrayList()

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
