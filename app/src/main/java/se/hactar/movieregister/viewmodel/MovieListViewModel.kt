package se.hactar.movieregister.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import se.hactar.movieregister.MovieApp
import se.hactar.movieregister.model.Movie

class MovieListViewModel : ViewModel() {

    fun liveDataMovies(): LiveData<List<Movie>> {
        return MovieApp.database.movieDao().allLiveData
    }
}
