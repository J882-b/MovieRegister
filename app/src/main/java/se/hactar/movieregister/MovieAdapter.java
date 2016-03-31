package se.hactar.movieregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import se.hactar.movieregister.data.Movie;

class MovieAdapter extends ArrayAdapter<Movie> {
    //------------------------------------------------------------ class (static)

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

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }

        TextView textView = (TextView) view;
        textView.setText(getItem(position).getName());
        return view;
    }

}