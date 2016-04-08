package se.hactar.movieregister;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
    //------------------------------------------------------------ class (static)
    private static final String TAG = MovieAdapter.class.getSimpleName();

    //------------------------------------------------------------ object (not static)
    private final LayoutInflater mInflater;
    private final int mResource;

    public MovieAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        mInflater = LayoutInflater.from(context);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = mInflater.inflate(R.layout.movie_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.poster = (ImageView) view.findViewById(R.id.poster);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.index = (TextView) view.findViewById(R.id.index);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        if (PosterHelper.getPosterFile(getItem(position).getId()).exists()) {
            File posterFile = PosterHelper.getPosterFile(getItem(position).getId());
            Log.d(TAG, "posterFile=" + posterFile.getAbsolutePath());
            viewHolder.poster.setImageBitmap(BitmapFactory.decodeFile(posterFile.getAbsolutePath()));
        } else {
            // TODO: Change to a better drawable.
            viewHolder.poster.setImageResource(R.drawable.ic_coins_l);
            PosterAsyncTask.request(getItem(position).getId(), this);
        }
        viewHolder.name.setText(getItem(position).getName());
        viewHolder.index.setText(getItem(position).getIndex());
        return view;
    }

    private static class ViewHolder {
        ImageView poster;
        TextView name;
        TextView index;
    }

}