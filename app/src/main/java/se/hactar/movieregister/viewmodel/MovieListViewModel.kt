package se.hactar.movieregister.viewmodel


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import se.hactar.movieregister.MovieApp
import se.hactar.movieregister.model.Movie

class MovieListViewModel : ViewModel() {

    fun liveDataMovies(): LiveData<List<Movie>> {
        return MovieApp.getDb().movieDao().allLiveData
    }
}
