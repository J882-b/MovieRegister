package se.hactar.movieregister.util;

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

import se.hactar.movieregister.MovieApplication;

public class PosterHelper {
    //------------------------------------------------------------ class (static)
    private static final String TAG = PosterHelper.class.getSimpleName();

    private static int downloads = 0;

    public static File getPosterFile(String imdbId) {
        return new File(getPosterDir(), imdbId + ".jpg");
    }

    private static File getPosterDir() {
        File cacheDir = MovieApplication.getContext().getCacheDir();
        File posterDir = new File(cacheDir, "poster");
        if (!posterDir.exists()) {
            posterDir.mkdir();
        }
        return posterDir;
    }

    //------------------------------------------------------------ object (not static)
    private String imdbId;

    public PosterHelper(String imdbId) {
        this.imdbId = imdbId;
    }

    public Boolean download() {
        InputStream jsonInputStream = null;
        InputStream posterInputStream = null;
        try {
            downloads += 1;
            Log.v(TAG, "Number of poster downloads started is " + downloads);
            jsonInputStream = getJsonURL().openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"));
            posterInputStream = posterUrl(reader).openConnection().getInputStream();
            posterToFile(posterInputStream);
        } catch (Exception e) {
            Log.e(TAG, "Problem while downloadin poster image.", e);
            return false;
        } finally {
            try {
                if (jsonInputStream != null) {
                    jsonInputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Problem closing jsonInputStream", e);
                return false;
            }
            try {
                if (posterInputStream != null) {
                    posterInputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Problem closing posterInputStream.", e);
                return false;
            }
        }
        return true;
    }

    private URL getJsonURL() throws MalformedURLException {
        String jsonUrl = "http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json";
        Log.d(TAG, "jsonUrl=" + jsonUrl);
        return new URL(jsonUrl);
    }

    private void posterToFile(InputStream inputStream) throws IOException {
        // TODO: Scale image to a smaller resolution if needed.
        File file = getPosterFile(imdbId);
        Log.d(TAG, "Downloading to file " + file.getAbsolutePath());
        FileOutputStream output = new FileOutputStream(file);
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while (true) {
            len = inputStream.read(buffer);
            if (len == -1) {
                break;
            }
            output.write(buffer, 0, len);
        }
        output.flush();
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
        String posterUrl = (String) new JSONObject(sb.toString()).get("Poster");
        Log.d(TAG, posterUrl);
        return new URL(posterUrl);
    }
}