package se.hactar.movieregister;

import android.app.Application;
import android.content.Context;

public class MovieApplication extends Application {
    //------------------------------------------------------------ class (static)
    private static Context context;

    public static Context getContext() {
        return context;
    }

    //------------------------------------------------------------ object (not static)
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
