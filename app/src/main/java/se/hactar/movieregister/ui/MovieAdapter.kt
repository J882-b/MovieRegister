package se.hactar.movieregister.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import se.hactar.movieregister.R
import se.hactar.movieregister.helper.UiHelper
import se.hactar.movieregister.model.Movie
import timber.log.Timber

internal class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = ArrayList<Movie>()

    private val onClickListener = lambda@{ view: View ->
        val movie = (view.tag as MovieViewHolder).movie
        if (TextUtils.isEmpty(movie.posterUrl)) {
            // IMDB ID is missing.
            return@lambda
        }
        UiHelper.openExternal(view.context, movie.imdbUrl)
    }

    fun addAll(_movies: List<Movie>) {
        movies.clear()
        movies.addAll(_movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movie = movies[position]
        holder.name.text = holder.movie.name
        holder.index.text = holder.movie.index
        holder.view.setOnClickListener(onClickListener)
        holder.view.tag = holder

        holder.image.setImageResource(R.drawable.ic_menu_block)

        if (TextUtils.isEmpty(holder.movie.posterUrl)) {
            return
        }

        Timber.d("Fetching poster for " + holder.movie.imdbId)
        Glide.with(holder.view.context)
                .load(holder.movie.posterUrl)
                .into(holder.image)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.poster)
        val name: TextView = view.findViewById(R.id.name)
        val index: TextView = view.findViewById(R.id.index)
        lateinit var movie: Movie
    }
}
