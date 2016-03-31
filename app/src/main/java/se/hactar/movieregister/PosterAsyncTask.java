package se.hactar.movieregister;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

// TODO: Refactor to use RxAndroid
class PosterAsyncTask extends AsyncTask<String, Integer, Boolean> {
    //------------------------------------------------------------ class (static)
    private static final String TAG = PosterAsyncTask.class.getSimpleName();

    //------------------------------------------------------------ object (not static)
    @Override
    protected Boolean doInBackground(String... params) {
        String imdbId = params[0];
        InputStream jsonInputStream = null;
        InputStream posterInputStream = null;
        try {
            URL jsonUrl = new URL("http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json");
            URLConnection urlConnection = jsonUrl.openConnection();
            jsonInputStream = jsonUrl.openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"));
            URL posterUrl = posterUrl(reader);
            posterInputStream = posterUrl.openConnection().getInputStream();
            File cacheDir = MovieApplication.getContext().getCacheDir();
            streamToFile(posterInputStream, new File(cacheDir, imdbId + ".jpg"));

        } catch (Exception e) {
            Log.e(TAG, "Problem while getting poster image.", e);
            return false;
        } finally {
            try {
                jsonInputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Problem closing jsonInputStream", e);
            }
            try {
                posterInputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Problem closing posterInputStream.", e);
            }
        }
        return true;
    }

    private void streamToFile(InputStream inputStream, File file) throws IOException {
        FileOutputStream output = new FileOutputStream(file);
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while (inputStream.available() > 0) {
            len = inputStream.read(buffer);
            output.write(buffer, 0, len);
        }
    }

    private URL posterUrl(BufferedReader reader) throws JSONException, MalformedURLException {
        StringBuffer sb = new StringBuffer();
        while (true) {
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                Log.e(TAG, "Error reading line.", e);
                break;
            }
            if (line == null) {
                break;
            }
            sb.append(line);
        }
        return new URL((String) new JSONObject(sb.toString()).get("Poster"));
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        // TODO: Got poster image. Tell UI.
    }
}