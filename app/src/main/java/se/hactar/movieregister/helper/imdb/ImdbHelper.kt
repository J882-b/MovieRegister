package se.hactar.movieregister.helper.imdb


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import se.hactar.movieregister.helper.imdb.model.Suggest

object ImdbHelper {

    const val BASE_URL = "http://sg.media-imdb.com/suggests/"
    const val TITLE_URL = "http://www.imdb.com/title/"

    interface Api {

        @GET(BASE_URL + "{firstLetter}/{search}.json")
        fun getSuggest(@Path("firstLetter") firstLetter: String,
                       @Path("search") search: String): Observable<Suggest>
    }
}
