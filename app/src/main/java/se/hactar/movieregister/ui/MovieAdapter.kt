package se.hactar.movieregister.ui

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import se.hactar.movieregister.R
import se.hactar.movieregister.api.imdb.Imdb
import se.hactar.movieregister.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {

    private val movies = ArrayList<Movie>()

    private val onClickListener = lambda@{ view: View ->
        val movie = (view.tag as MovieViewHolder).movie
        if (TextUtils.isEmpty(movie.imdbId)) {
            // IMDB ID is missing.
            return@lambda
        }
        view.context.startActivity(Imdb.openExternalIntent(movie.imdbId))
    }

    fun addAll(_movies: List<Movie>) {
        movies.clear()
        movies.addAll(_movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        view.setOnClickListener(onClickListener)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        private val TAG = MovieViewHolder::class.java.simpleName
    }

    private val image: ImageView = view.findViewById(R.id.poster)
    private val name: TextView = view.findViewById(R.id.name)
    private val index: TextView = view.findViewById(R.id.index)
    lateinit var movie: Movie

    fun bind(_movie: Movie) {
        movie = _movie
        name.text = "${movie.title} ${movie.indexTitle}"
        index.text = movie.index
        view.tag = this
        image.setImageResource(R.drawable.ic_menu_block)

        if (TextUtils.isEmpty(movie.posterUrl)) {
            return
        }

        Log.d(TAG, "Fetching poster for ${movie.imdbId}")
        Glide.with(view.context).load(movie.posterUrl).into(image)
    }
}
