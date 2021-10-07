package se.hactar.movieregister.api.imdb

import android.content.Intent
import android.net.Uri
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import se.hactar.movieregister.api.imdb.model.Suggest

private const val TITLE_URL = "https://www.imdb.com/title/"

// https://stackoverflow.com/questions/1966503/does-imdb-provide-an-api
private const val BASE_URL = "https://sg.media-imdb.com/suggests/"

object Imdb {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonPConverterFactory(Gson()))
        .build()
    private val imdb = retrofit.create(ImdbApi::class.java)

    fun openExternalIntent(imdbId: String?): Intent {
        val url = "$TITLE_URL$imdbId/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        return intent
    }

    suspend fun getSuggest(search: String): Suggest {
        return imdb.getSuggest(search.firstLetter(), search)
    }

    private fun String.firstLetter(): String {
        return this.substring(0, 1)
    }
}

private interface ImdbApi {

    @GET("$BASE_URL{firstLetter}/{search}.json")
    suspend fun getSuggest(
        @Path("firstLetter") firstLetter: String,
        @Path("search") search: String
    ): Suggest
}
