package se.hactar.movieregister;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import se.hactar.movieregister.util.PosterHelper;

public class PosterAsyncTask extends AsyncTask<String, Integer, Boolean> {
    //------------------------------------------------------------ class (static)
    private static String TAG = PosterAsyncTask.class.getSimpleName();

    /**
     * To keep track of what posters have been requested for download.
     * <p/>
     * Usage of requestedPosters need to be syncronized because it will be called from main and
     * background.
     */
    private static List<String> requestedPosters = new ArrayList<String>();

    /**
     * Used to request a poster to be downloaded using AsyncTask.
     *
     * Repeated calls for a imdbId that have not been downloaded yet will be ignored.
     *
     * @param imdbId IMDB Id for poster to download.
     * @param adapter Who to notify when the download is complete.
     */
    public static void request(String imdbId, BaseAdapter adapter) {
        synchronized (requestedPosters) {
            if (requestedPosters.contains(imdbId)) {
                Log.d(TAG, "Poster already requested for imdbId=" + imdbId);
            } else {
                requestedPosters.add(imdbId);
                new PosterAsyncTask(imdbId, adapter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }

    //------------------------------------------------------------ object (not static)
    private final String imdbId;
    private final BaseAdapter adapter;
    private final PosterHelper posterHelper;

    private PosterAsyncTask(String imdbId, BaseAdapter adapter) {
        this.imdbId = imdbId;
        this.adapter = adapter;
        this.posterHelper = new PosterHelper(imdbId);
    }

    @Override
    protected Boolean doInBackground(String[] params) {
        try {
            if (imdbId != null) {
                return posterHelper.download();
            }
            return false;
        } finally {
            synchronized (requestedPosters) {
                requestedPosters.remove(imdbId);
                Log.v(TAG, "Requested posters remaining is " + requestedPosters.size());
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // TODO: Only refresh one row.
            adapter.notifyDataSetChanged();
        }
    }
}
