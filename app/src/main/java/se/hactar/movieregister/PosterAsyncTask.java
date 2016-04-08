package se.hactar.movieregister;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;

import se.hactar.movieregister.util.PosterHelper;

public class PosterAsyncTask extends AsyncTask<String, Integer, Boolean> {
    //------------------------------------------------------------ class (static)

    //------------------------------------------------------------ object (not static)
    private final PosterHelper posterHelper = new PosterHelper();
    private final String imdbId;

    public PosterAsyncTask(String imdbId)  {
        this.imdbId = imdbId;
    }

    @Override
    protected Boolean doInBackground(String[] params) {
        if (imdbId != null) {
            return posterHelper.downloadPoster(imdbId);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // TODO: refresh adapter position for this poster.
        }
    }
}
