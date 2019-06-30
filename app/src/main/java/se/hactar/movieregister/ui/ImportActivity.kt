package se.hactar.movieregister.ui

import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import se.hactar.movieregister.repository.MovieRepository

class ImportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val action = intent.action

        if (action == Intent.ACTION_VIEW) {
            val scheme = intent.scheme
            val resolver = contentResolver

            val uri = intent.data
            val name = uri.lastPathSegment;

            Log.v("tag" , "File intent detected: " + action + " : " + intent.dataString + " : " + intent.type + " : " + name);

            val input = resolver.openInputStream(uri);
            MovieRepository.importMovies(input)
        }

        startMain()
        finish()
    }

    private fun startMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
    }

}