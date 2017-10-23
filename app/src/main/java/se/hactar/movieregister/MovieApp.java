package se.hactar.movieregister;

import android.app.Application;
import android.arch.persistence.room.Room;

import se.hactar.movieregister.model.MovieDatabase;
import timber.log.Timber;

public class MovieApp extends Application {
    private static MovieApp app;
    private static MovieDatabase db;

    public static MovieApp getApp() {
        return app;
    }

    public static MovieDatabase getDb() { return db; }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        db = Room.databaseBuilder(this, MovieDatabase.class, "movie_db").build();

        // TODO: disable logging for release build
        Timber.plant(new Timber.DebugTree());
    }
}
