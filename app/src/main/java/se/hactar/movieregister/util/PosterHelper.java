package se.hactar.movieregister.util;

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
import timber.log.Timber;

public class PosterHelper {
    private static int downloads = 0;

    public static File getPosterFile(final String imdbId) {
        return new File(getPosterDir(), imdbId + ".jpg");
    }

    private static File getPosterDir() {
        File cacheDir = MovieApplication.getApp().getCacheDir();
        File posterDir = new File(cacheDir, "poster");
        if (!posterDir.exists()) {
            boolean success = posterDir.mkdir();
            if (success) {
                Timber.d("Created dir" + posterDir.getAbsolutePath());
            }
        }
        return posterDir;
    }

    private final String imdbId;

    public PosterHelper(final String imdbId) {
        this.imdbId = imdbId;
    }

    // TODO: Convert to Retrofit and RxJava
    public Boolean download() {
        InputStream jsonInputStream = null;
        InputStream posterInputStream = null;
        try {
            downloads += 1;
            Timber.v("Number of poster downloads started is " + downloads);
            jsonInputStream = getJsonURL().openConnection().getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"));
            posterInputStream = posterUrl(reader).openConnection().getInputStream();
            posterToFile(posterInputStream);
            return true;
        } catch (Exception e) {
            Timber.e("Problem while downloadin poster image.", e);
            return false;
        } finally {
            try {
                if (jsonInputStream != null) {
                    jsonInputStream.close();
                }
            } catch (IOException e) {
                Timber.e("Problem closing jsonInputStream", e);
            }
            try {
                if (posterInputStream != null) {
                    posterInputStream.close();
                }
            } catch (IOException e) {
                Timber.e("Problem closing posterInputStream.", e);
            }
        }
    }

    private URL getJsonURL() throws MalformedURLException {
        String jsonUrl = "http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json";
        Timber.d("jsonUrl=" + jsonUrl);
        return new URL(jsonUrl);
    }

    private void posterToFile(InputStream inputStream) throws IOException {
        // TODO: Scale image to a smaller resolution if needed.
        File file = getPosterFile(imdbId);
        Timber.d("Downloading to file " + file.getAbsolutePath());
        FileOutputStream output = new FileOutputStream(file);
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while (true) {
            len = inputStream.read(buffer);
            if (len == -1) {
                break;
            }
            output.write(buffer, 0, len);
        }
        output.flush();
    }

    private URL posterUrl(final BufferedReader reader) throws JSONException, MalformedURLException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                Timber.e("Error reading line.", e);
                break;
            }
            if (line == null) {
                break;
            }
            sb.append(line);
        }
        String posterUrl = (String) new JSONObject(sb.toString()).get("Poster");
        Timber.d(posterUrl);
        return new URL(posterUrl);
    }
}
