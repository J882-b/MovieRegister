package se.hactar.movieregister;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.util.PosterHelper;
import timber.log.Timber;

class PosterAsyncTask extends AsyncTask<String, Integer, Boolean> {

    /**
     * To keep track of what posters have been requested for download.
     * <p/>
     * Usage of REQUESTED_POSTERS need to be syncronized because it will be called from main and
     * background.
     */
    private static final List<String> REQUESTED_POSTERS = new ArrayList<>();

    /**
     * Used to request a poster to be downloaded using AsyncTask.
     *
     * Repeated calls for a imdbId that have not been downloaded yet will be ignored.
     *
     * @param imdbId IMDB Id for poster to download.
     * @param adapter Who to notify when the download is complete.
     */
    public static void request(final String imdbId, final RecyclerView.Adapter adapter) {
        synchronized (REQUESTED_POSTERS) {
            if (REQUESTED_POSTERS.contains(imdbId)) {
                Timber.d("Poster already requested for imdbId=" + imdbId);
            } else {
                REQUESTED_POSTERS.add(imdbId);
                new PosterAsyncTask(imdbId, adapter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    private final String imdbId;
    private final RecyclerView.Adapter adapter;
    private final PosterHelper posterHelper;

    private PosterAsyncTask(final String imdbId, final RecyclerView.Adapter adapter) {
        this.imdbId = imdbId;
        this.adapter = adapter;
        this.posterHelper = new PosterHelper(imdbId);
    }

    @Override
    protected Boolean doInBackground(final String[] params) {
        try {
            if (imdbId != null) {
                return posterHelper.download();
            }
            return false;
        } finally {
            synchronized (REQUESTED_POSTERS) {
                REQUESTED_POSTERS.remove(imdbId);
                Timber.v("Requested posters remaining is " + REQUESTED_POSTERS.size());
            }
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            // TODO: only refresh one row
            adapter.notifyDataSetChanged();
        }
    }
}
