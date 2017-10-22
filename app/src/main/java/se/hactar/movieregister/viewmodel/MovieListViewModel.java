package se.hactar.movieregister.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import se.hactar.movieregister.db.Movie;
import se.hactar.movieregister.repository.MovieRepository;

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
        liveDataMovies.setValue(MovieRepository.getInstance().getMovies());
    }

}
