package se.hactar.movieregister;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieListFragment extends Fragment {
    private MovieListViewModel model;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView listView = view.findViewById(R.id.movies);
        MovieAdapter adapter = new MovieAdapter();
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        model = ViewModelProviders.of(getActivity()).get(MovieListViewModel.class);
        model.getLiveDataMovies().observe(this, adapter::addAll);
        return view;
    }
}
