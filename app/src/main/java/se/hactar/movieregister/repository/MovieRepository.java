package se.hactar.movieregister.repository;


import android.content.res.Resources;
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
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import se.hactar.movieregister.MovieApp;
import se.hactar.movieregister.R;
import se.hactar.movieregister.helper.GsonPConverterFactory;
import se.hactar.movieregister.helper.ImportMovie;
import se.hactar.movieregister.helper.imdb.ImdbHelper;
import se.hactar.movieregister.helper.imdb.model.Result;
import se.hactar.movieregister.helper.imdb.model.Suggest;
import se.hactar.movieregister.model.Movie;
import se.hactar.movieregister.model.MovieDao;
import timber.log.Timber;

public class MovieRepository {
    private static MovieRepository instance;
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ImdbHelper.Api.BASE_URL)
            .addConverterFactory(new GsonPConverterFactory(new Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build();
    private final ImdbHelper.Api imdb = retrofit.create(ImdbHelper.Api.class);
    private final MovieDao movieDao = MovieApp.db.movieDao();

    private MovieRepository() {
    }

    public static synchronized MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public void importMovies() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Movie> movies = new ArrayList<>();

                Resources resources = MovieApp.app.getResources();
                try (InputStream inputStream = resources.openRawResource(R.raw.filmregister);
                     InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                     BufferedReader reader = new BufferedReader(inputStreamReader)) {

                    String inputLine;
                    while (true) {
                        try {
                            inputLine = reader.readLine();
                        } catch (IOException e) {
                            Timber.e(e, "Error while reading a line from file");
                            break;
                        }
                        if (inputLine == null) {
                            // Exit loop if no lines left
                            break;
                        }
                        Movie movie = ImportMovie.parse(inputLine);
                        movies.add(movie);
                    }
                } catch (IOException e) {
                    Timber.e("Prolem importing movies from file.");
                }

                movieDao.insertAll(movies);
                downloadPosterUrls();
            }
        }).start();
    }

    private void downloadPosterUrls() {
        Observable.fromIterable(movieDao.getAll())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter(movie -> !TextUtils.isEmpty(movie.getImdbId()))
                .concatMap(movie -> imdb.getSuggest(firstLetter(movie.getImdbId()), movie.getImdbId()))
                .map(this::createIdUrlEntry)
                .subscribe(this::setPosterUrlInDb, Timber::e);
    }

    private Map.Entry<String, String> createIdUrlEntry(final Suggest suggest) {
        Result result = suggest.getFirstResult();
        return new AbstractMap.SimpleEntry<>(result.getId(), result.getImageUrl());
    }

    private void setPosterUrlInDb(final Map.Entry<String, String> entry) {
        Movie movie = movieDao.get(entry.getKey());
        movie.setPosterUrl(entry.getValue());
        movieDao.update(movie);
    }

    private String firstLetter(final String string) {
        return string.substring(0, 1);
    }
}
