package se.hactar.movieregister;

import android.app.Application;

import timber.log.Timber;

public class MovieApplication extends Application {
    private static MovieApplication app;

    public static MovieApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        // TODO: disable logging for release build
        Timber.plant(new Timber.DebugTree());
    }
}
