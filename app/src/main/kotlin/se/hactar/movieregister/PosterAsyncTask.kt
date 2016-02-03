package se.hactar.movieregister

import android.content.Context
import android.os.AsyncTask
import org.json.JSONObject
import java.io.*
import java.net.URL

/**
 * Created by johan on 03/02/16.
 */
public class PosterAsyncTask(val context: Context) : AsyncTask<String, Integer, Boolean>() {

    override fun doInBackground(vararg params: String?): Boolean? {
        val imdbId = params[0]
        val jsonUrl = URL("http://www.omdbapi.com/?i=" + imdbId + "&plot=short&r=json")
        val urlConnection = jsonUrl.openConnection()
        var jsonInputStream: InputStream? = null
        var posterInputStream: InputStream? = null
        try {
            val jsonInputStream = jsonUrl.openConnection().inputStream
            val reader = BufferedReader(InputStreamReader(jsonInputStream, "UTF-8"))
            val posterUrl = posterUrl(reader)
            val posterInputStream = posterUrl.openConnection().inputStream
            streamToFile(posterInputStream, File(context.cacheDir, imdbId + ".jpg"))

        } catch (e: Exception) {
            return false
        } finally {
            jsonInputStream!!.close()
            posterInputStream!!.close()
        }
        return true
    }

    private fun streamToFile(inputStream: InputStream?, file: File) {
        val output = FileOutputStream(file);
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream!!.available() > 0) {
            len = inputStream.read(buffer)
            output.write(buffer, 0, len)
        }
    }

    private fun posterUrl(reader: BufferedReader): URL {
        val sb = StringBuffer()
        while (true) {
            val line = reader.readLine()
            if (line == null) {
                break
            }
            sb.append(line)
        }
        return URL(JSONObject(sb.toString()).get("Poster") as String)
    }

    override fun onPostExecute(result: Boolean?) {
        // TODO: Got poster image. Tell UI.
    }
}