package se.hactar.movieregister;

import android.os.AsyncTask;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import se.hactar.movieregister.util.PosterHelper;

public class PosterAsyncTask extends AsyncTask<String, Integer, Boolean> {
    //------------------------------------------------------------ class (static)

    //------------------------------------------------------------ object (not static)
    private final String imdbId;
    private final BaseAdapter adapter;
    private final PosterHelper posterHelper;

    public PosterAsyncTask(String imdbId, BaseAdapter adapter)  {
        this.imdbId = imdbId;
        this.adapter = adapter;
        this.posterHelper = new PosterHelper(imdbId);
    }

    @Override
    protected Boolean doInBackground(String[] params) {
        if (imdbId != null) {
            return posterHelper.download();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // TODO: Only refresh one row.
            adapter.notifyDataSetChanged();
        }
    }
}
