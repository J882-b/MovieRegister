package se.hactar.movieregister.util;

import android.os.Environment;
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

// TODO: Refactor to use RxAndroid
public class PosterHelper {
    //------------------------------------------------------------ class (static)
    private static final String TAG = PosterHelper.class.getSimpleName();

    public static File getPosterFile(String imdbId) {
        return new File(getPosterDir(), imdbId + ".jpg");
    }

    public static File getPosterDir() {

        File cacheDir = MovieApplication.getContext().getCacheDir();
        //File cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File posterDir = new File(cacheDir, "poster");
        if (!posterDir.exists()) {
            posterDir.mkdir();
        }
        return posterDir;
    }
    //------------------------------------------------------------ object (not static)

    public Boolean downloadPoster(String imdbId) {
        InputStream jsonInputStream = null;
        InputStream posterInputStream = null;
        try {
            jsonInputStream = getJsonURL(imdbId).openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"));
            URL posterUrl = posterUrl(reader);
            posterInputStream = posterUrl.openConnection().getInputStream();
            streamToFile(posterInputStream, getPosterFile(imdbId));
        } catch (Exception e) {
            Log.e(TAG, "Problem while downloadin poster image.", e);
            return false;
        } finally {
            try {
                jsonInputStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Problem closing jsonInputStream", e);
                return false;
            }
            try {
                posterInputStream.close();
            } catch (Exception e) {
                Log.e(TAG, "Problem closing posterInputStream.", e);
                return false;
            }
        }
        return true;
    }

    private URL getJsonURL(String imdbId) throws MalformedURLException {
        String jsonUrl = "http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json";
        Log.d(TAG, "jsonUrl=" + jsonUrl);
        return new URL(jsonUrl);
    }

    private void streamToFile(InputStream inputStream, File file) throws IOException {
        Log.d(TAG, "Downloading to file " + file.getAbsolutePath());
        FileOutputStream output = new FileOutputStream(file);
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
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