package se.hactar.movieregister.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import se.hactar.movieregister.repository.MovieRepository
import timber.log.Timber

class ImportActivity : AppCompatActivity() {

    private val tag: String = ImportActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val action = intent.action

        if (action == Intent.ACTION_VIEW) {
            val resolver = contentResolver

            val uri = intent.data
            val name = uri?.lastPathSegment

            Timber.v("$tag File intent detected: $action : ${intent.dataString} : ${intent.type} : $name")

            val input = resolver.openInputStream(uri!!)
            MovieRepository.importMovies(input!!)
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