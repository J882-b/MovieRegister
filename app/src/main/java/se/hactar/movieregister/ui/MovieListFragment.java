package se.hactar.movieregister.ui;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.hactar.movieregister.R;
import se.hactar.movieregister.helper.DebugLifcycleObserver;
import se.hactar.movieregister.viewmodel.MovieListViewModel;

public class MovieListFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        getLifecycle().addObserver(new DebugLifcycleObserver(this));
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = view.findViewById(R.id.movies);
        MovieAdapter adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MovieListViewModel model = ViewModelProviders.of(getActivity()).get(MovieListViewModel.class);
        model.getLiveDataMovies().observe(this, adapter::addAll);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
    }
}
