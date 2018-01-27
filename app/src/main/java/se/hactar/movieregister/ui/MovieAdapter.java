package se.hactar.movieregister.ui;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.R;
import se.hactar.movieregister.helper.UiHelper;
import se.hactar.movieregister.model.Movie;
import timber.log.Timber;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final int IMAGE_SIZE = 200;

    private final List<Movie> movies = new ArrayList<>();

    private final View.OnClickListener onClickListener = view -> {
        Movie movie = ((MovieViewHolder) view.getTag()).movie;
        if (TextUtils.isEmpty(movie.getPosterUrl())) {
            // IMDB ID is missing.
            return;
        }
        UiHelper.openExternal(view.getContext(), movie.getImdbUrl());
    };

    public void addAll(final List<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.movie = movies.get(position);
        holder.name.setText(holder.movie.getName());
        holder.index.setText(holder.movie.getIndex());
        holder.view.setOnClickListener(onClickListener);
        holder.view.setTag(holder);

        holder.image.setImageResource(R.drawable.ic_menu_block);

        if (TextUtils.isEmpty(holder.movie.getPosterUrl())) {
            return;
        }

        Timber.d("Fetching poster for " + holder.movie.getImdbId());
        Glide.with(holder.view.getContext())
                .load(holder.movie.getPosterUrl())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onViewRecycled(final MovieViewHolder holder) {
        cleanup(holder);
    }

    private void cleanup(final MovieViewHolder holder) {
        Glide.with(holder.image.getContext()).clear(holder.image);
        holder.image.setImageDrawable(null);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;
        public final TextView name;
        public final TextView index;
        public final View view;
        public Movie movie;

        public MovieViewHolder(final View view) {
            super(view);
            image = view.findViewById(R.id.poster);
            name = view.findViewById(R.id.name);
            index = view.findViewById(R.id.index);
            this.view = view;
        }
    }
}
