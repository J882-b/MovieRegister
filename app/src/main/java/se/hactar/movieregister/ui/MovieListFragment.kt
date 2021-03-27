package se.hactar.movieregister.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import se.hactar.movieregister.R
import se.hactar.movieregister.helper.DebugLifecycleObserver
import se.hactar.movieregister.viewmodel.MovieListViewModel

class MovieListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycle.addObserver(DebugLifecycleObserver(this))
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        recyclerView = view.findViewById(R.id.movies)
        val adapter = MovieAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val model = ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        model.liveDataMovies().observe(viewLifecycleOwner, { adapter.addAll(it!!) })
        return   view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView.adapter = null
    }
}
