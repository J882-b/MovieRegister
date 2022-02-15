package se.hactar.movieregister.ui

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.app.AppCompatActivity
import se.hactar.movieregister.R
import se.hactar.movieregister.repository.MovieRepository

class MainActivity : AppCompatActivity() {
    private val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
        val input = contentResolver.openInputStream(uri!!)
        MovieRepository.importMovies(input!!)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        when (item.itemId) {
            R.id.action_clear -> {
                MovieRepository.clearMovies()
                return true
            }
            R.id.action_open -> {
                getContent.launch("*/*")
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
