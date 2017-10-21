package se.hactar.movieregister;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import se.hactar.movieregister.data.Movie;

class MovieItemClickListener implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        Movie movie = (Movie) parent.getAdapter().getItem(position);
        if (movie.getId().length() == 0) {
            // TODO: Dialog that say IMDB ID is missing.
            return;
        }
        String url = "http://www.imdb.com/title/" + movie.getId() + "/";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        parent.getContext().startActivity(intent);
    }
}