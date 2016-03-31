package se.hactar.movieregister


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import se.hactar.movieregister.R
import data.Movie
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main, container, false)
        val listView = view.findViewById(R.id.movies) as ListView
        val movies = readMovies()
        val adapter = MovieAdapter(activity, android.R.layout.simple_list_item_1, movies)
        listView.adapter = adapter
        listView.onItemClickListener = MovieItemClickListener()
        return view;
    }

    fun readMovies() : MutableList<Movie> {
        val movies = arrayListOf<Movie>()
        val inputStream = getResources().openRawResource(R.raw.filmregister)
        val reader = BufferedReader(InputStreamReader(inputStream))

        var first = true
        var inputLine : String?
        while (true) {
            inputLine = reader.readLine()
            if (inputLine == null) {
                // Exit loop if no lines left
                break
            }
            if (first) {
                // First row is column titles
                first = false
                continue
            }
            val movie = Movie.Factory.parse(inputLine)
            movies.add(movie)
        }
        return movies
    }
}
