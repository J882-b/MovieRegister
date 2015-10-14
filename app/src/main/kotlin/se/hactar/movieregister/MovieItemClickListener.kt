package se.hactar.movieregister

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import se.hactar.movieregister.data.Movie

/**
 * Created by dilbert on 2015-10-14.
 */
class MovieItemClickListener : AdapterView.OnItemClickListener {
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val adapter = parent!!.adapter as ArrayAdapter<Movie>
        val movie = adapter.getItem(position)
        if (movie.imdbId.length() == 0) {
            // TODO: Dialog that say IMDB ID is missing.
            return
        }
        val url = "http://www.imdb.com/title/" + movie.imdbId + "/"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        adapter.context.startActivity(intent)
    }
}