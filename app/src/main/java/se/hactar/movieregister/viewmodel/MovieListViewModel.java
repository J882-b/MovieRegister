package se.hactar.movieregister.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import se.hactar.movieregister.MovieApp;
import se.hactar.movieregister.model.Movie;

public class MovieListViewModel extends ViewModel {

    public LiveData<List<Movie>> getLiveDataMovies() {
        return MovieApp.getDb().movieDao().getAllLiveData();
    }
}
