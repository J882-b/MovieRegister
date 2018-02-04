package se.hactar.movieregister.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import se.hactar.movieregister.R
import se.hactar.movieregister.helper.DebugLifcycleObserver
import se.hactar.movieregister.model.Movie
import se.hactar.movieregister.viewmodel.MovieListViewModel

class MovieListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycle.addObserver(DebugLifcycleObserver(this))
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)
        recyclerView = view.findViewById(R.id.movies)
        val adapter = MovieAdapter()
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        val model = ViewModelProviders.of(activity!!).get(MovieListViewModel::class.java)
        model.liveDataMovies().observe(this, Observer<List<Movie>> { adapter.addAll(it!!) })
        return   view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView!!.adapter = null
    }
}
