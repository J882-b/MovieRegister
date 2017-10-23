package se.hactar.movieregister.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import se.hactar.movieregister.R;
import se.hactar.movieregister.helper.DebugLifcycleObserver;
import se.hactar.movieregister.repository.MovieRepository;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        getLifecycle().addObserver(new DebugLifcycleObserver(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_import) {
            MovieRepository.getInstance().importMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
