package se.hactar.movieregister;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import se.hactar.movieregister.data.Movie;
import se.hactar.movieregister.util.PosterHelper;

class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String TAG = MovieAdapter.class.getSimpleName();

    private final LayoutInflater inflater;
    private final int resource;

    MovieAdapter(final Context context, final List<Movie> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        inflater = LayoutInflater.from(context);
        resource = android.R.layout.simple_list_item_1;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, final @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.movie_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.poster = view.findViewById(R.id.poster);
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.index = view.findViewById(R.id.index);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Movie movie = getItem(position);
        if (movie == null) {
            movie = Movie.NULL_MOVIE;
        }

        if (PosterHelper.getPosterFile(movie.getId()).exists()) {
            File posterFile = PosterHelper.getPosterFile(movie.getId());
            Log.d(TAG, "posterFile=" + posterFile.getAbsolutePath());
            viewHolder.poster.setImageBitmap(BitmapFactory.decodeFile(posterFile.getAbsolutePath()));
        } else {
            // TODO: Change to a better drawable.
            viewHolder.poster.setImageResource(R.drawable.ic_menu_block);
            PosterAsyncTask.request(movie.getId(), this);
        }
        viewHolder.name.setText(movie.getName());
        viewHolder.index.setText(movie.getIndex());
        return view;
    }

    private static class ViewHolder {
        ImageView poster;
        TextView name;
        TextView index;
    }

}
