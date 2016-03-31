package se.hactar.movieregister;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.data.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    //------------------------------------------------------------ class (static)
    private static final String TAG = MainActivityFragment.class.getSimpleName();

    //------------------------------------------------------------ object (not static)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) view.findViewById(R.id.movies);
        ListAdapter listAdapter = new MovieAdapter(getActivity(), android.R.layout.simple_list_item_1, readMovies());
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new MovieItemClickListener());
        return view;
    }


    private List<Movie> readMovies() {
        InputStream inputStream = getResources().openRawResource(R.raw.filmregister);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        boolean first = true;
        String inputLine;
        List<Movie> movies = new ArrayList<Movie>();
        while (true) {
            try {
                inputLine = reader.readLine();
            } catch (IOException e) {
                Log.e(TAG, "Error while reading a line from file", e);
                break;
            }
            if (inputLine == null) {
                // Exit loop if no lines left
                break;
            }
            if (first) {
                // First row is column titles
                first = false;
                continue;
            }
            Movie movie = Movie.parse(inputLine);
            movies.add(movie);
        }
        return movies;
    }
}
