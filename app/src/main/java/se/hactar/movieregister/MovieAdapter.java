package se.hactar.movieregister;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.db.Movie;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movies = new ArrayList<>();

    private final View.OnClickListener onClickListener = view -> {
        Movie movie = ((MovieViewHolder) view.getTag()).movie;
        if (movie.getImdbId().length() == 0) {
            // TODO: Dialog that say IMDB ID is missing.
            return;
        }
        openExternal(view.getContext(), movie);
    };

    // TODO: move to util
    private void openExternal(final Context context, final Movie movie) {
        String url = "http://www.imdb.com/title/" + movie.getImdbId() + "/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

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

        holder.poster.setImageResource(R.drawable.ic_menu_block);

        if (TextUtils.isEmpty(holder.movie.getPosterUrl())) {
            //TODO: Trigger fetch of URL?
            return;
        }

        Picasso.with(holder.view.getContext())
                .load(holder.movie.getPosterUrl())
                .error(R.drawable.ic_menu_block)
                .resize(100, 100)
                .into(holder.poster);
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
