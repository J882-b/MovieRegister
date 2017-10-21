package se.hactar.movieregister;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.data.Movie;
import se.hactar.movieregister.util.PosterHelper;
import timber.log.Timber;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movies = new ArrayList<>();

    private final View.OnClickListener onClickListener = view -> {
        Movie movie = ((MovieViewHolder) view.getTag()).movie;
        if (movie.getId().length() == 0) {
            // TODO: Dialog that say IMDB ID is missing.
            return;
        }
        openExternal(view.getContext(), movie);
    };

    // TODO: move to util
    private void openExternal(final Context context, Movie movie) {
        String url = "http://www.imdb.com/title/" + movie.getId() + "/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public void addAll(final List<Movie> pMovies) {
        movies.clear();
        movies.addAll(pMovies);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_row, parent, false);
        MovieViewHolder holder = new MovieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.movie = movies.get(position);
        holder.name.setText(holder.movie.getName());
        holder.index.setText(holder.movie.getIndex());
        holder.view.setOnClickListener(onClickListener);
        holder.view.setTag(holder);

        // TODO: Use Picasso
        // TODO: Move to Repository
        if (PosterHelper.getPosterFile(holder.movie.getId()).exists()) {
            File posterFile = PosterHelper.getPosterFile(holder.movie.getId());
            Timber.d("posterFile=" + posterFile.getAbsolutePath());
            holder.poster.setImageBitmap(BitmapFactory.decodeFile(posterFile.getAbsolutePath()));
        } else {
            // TODO: Change to a better drawable.
            holder.poster.setImageResource(R.drawable.ic_menu_block);
            PosterAsyncTask.request(holder.movie.getId(), this);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        public Movie movie;
        public final ImageView poster;
        public final TextView name;
        public final TextView index;
        public final View view;

        public MovieViewHolder(final View view) {
            super(view);
            poster = view.findViewById(R.id.poster);
            name = view.findViewById(R.id.name);
            index = view.findViewById(R.id.index);
            this.view = view;
        }
    }
}
