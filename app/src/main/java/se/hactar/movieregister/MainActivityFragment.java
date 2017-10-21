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
import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = view.findViewById(R.id.movies);
        ListAdapter listAdapter = new MovieAdapter(getActivity(), readMovies());
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new MovieItemClickListener());
        return view;
    }


    private List<Movie> readMovies() {
        InputStream inputStream = getResources().openRawResource(R.raw.filmregister);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        boolean first = true;
        String inputLine;
        List<Movie> movies = new ArrayList<>();
        while (true) {
            try {
                inputLine = reader.readLine();
            } catch (IOException e) {
                Timber.e("Error while reading a line from file", e);
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
