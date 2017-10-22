package se.hactar.movieregister.repository;


import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import se.hactar.movieregister.MovieApplication;
import se.hactar.movieregister.R;
import se.hactar.movieregister.db.Movie;
import se.hactar.movieregister.repository.dto.Suggest;
import timber.log.Timber;

public class MovieRepository {
    private static final String BASE_URL = "http://sg.media-imdb.com/suggests/";
    private static MovieRepository instance;
    private static Gson gson = new Gson();

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new GsonPConverterFactory(new Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build();

    private final InstagramService instagramService = retrofit.create(InstagramService.class);

    public static synchronized MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
    }

    private Observable<Movie> getImportMovies() {
        InputStream inputStream = MovieApplication.getApp().getResources().openRawResource(R.raw.filmregister);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // TODO: Create real stream.
        String inputLine;
        List<Movie> movies = new ArrayList<>();
        while (true) {
            try {
                inputLine = reader.readLine();
            } catch (IOException e) {
                Timber.e("Error while reading a line from file", e);
                break;
            }
            if (inputLine == null) {
                // Exit loop if no lines left
                break;
            }
            Movie movie = Movie.parse(inputLine);
            movies.add(movie);
        }
        return Observable.fromIterable(movies);
    }

    public List<Movie> getMovies() {
        getPosterUrls(); // TODO: remove
        // TODO: Load from DB. Trigger import if DB empty.
        return getImportMovies().toList().blockingGet();
    }

    public void getPosterUrls() {
        // TODO: Maybe trigger one fetch if URL is missing.
        getImportMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(movie -> !TextUtils.isEmpty(movie.getImdbId()))
                .concatMap(movie -> instagramService.getSuggest(movie.getImdbId().substring(0,1), movie.getImdbId()))
                .map(this::getIdUrlEntry)
                .subscribe(entry -> Timber.d(entry.getKey() + ":" + entry.getValue()),
                        Timber::e, () -> Timber.d("Done!"));
        //TODO: Set URL on Movie in databse to trigger update to UI.
    }

    private Map.Entry<String, String> getIdUrlEntry(final Suggest suggest) {
        String url = "";
        try {
             url = suggest.results.get(0).image.get(0);
        } catch (Exception e) {
            Timber.i("No URL for: " + suggest.results.get(0).title);
        }

        return new AbstractMap.SimpleEntry<>(suggest.results.get(0).id, url);
    }

    private interface InstagramService {

        @GET(BASE_URL + "{firstLetter}/{search}.json")
        Observable<Suggest> getSuggest(@Path("firstLetter") String firstLetter,
                                       @Path("search") String search);

    }


}
