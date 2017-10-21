package se.hactar.movieregister;

import android.app.Application;

public class MovieApplication extends Application {
    private static MovieApplication app;

    public static MovieApplication getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
