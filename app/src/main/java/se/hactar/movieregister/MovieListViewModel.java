package se.hactar.movieregister;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.data.Movie;
import timber.log.Timber;

public class MovieListViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> liveDataMovies;

    public LiveData<List<Movie>> getLiveDataMovies() {
        if (liveDataMovies == null) {
            liveDataMovies = new MutableLiveData<>();
            loadMovies();
        }
        return liveDataMovies;
    }

    private void loadMovies() {
        //TODO: Async
        liveDataMovies.setValue(readMovies());
    }

    private List<Movie> readMovies() {
        InputStream inputStream = MovieApplication.getApp().getResources().openRawResource(R.raw.filmregister);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        boolean first = true;
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
            if (first) {
                // First row is column titles
                first = false;
                continue;
            }
            Movie movie = Movie.parse(inputLine);
            movies.add(movie);
        }
        return movies;
    }
}
