package se.hactar.movieregister.helper.imdb;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import se.hactar.movieregister.helper.imdb.model.Suggest;

public class ImdbHelper {

    public static final String TITLE_URL = "http://www.imdb.com/title/";

    public interface Api {
        String BASE_URL = "http://sg.media-imdb.com/suggests/";

        @GET(BASE_URL + "{firstLetter}/{search}.json")
        Observable<Suggest> getSuggest(@Path("firstLetter") String firstLetter,
                                       @Path("search") String search);
    }
}
