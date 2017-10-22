package se.hactar.movieregister.debug;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import timber.log.Timber;

public class DebugLifcycleObserver implements LifecycleObserver {

    private final String tag;

    public DebugLifcycleObserver(final LifecycleOwner lifecycleOwner) {
        this.tag = "<" + lifecycleOwner.getClass().getSimpleName() + "> ";
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.d(tag + Lifecycle.Event.ON_RESUME);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.d(tag + Lifecycle.Event.ON_PAUSE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Timber.d(tag + Lifecycle.Event.ON_START);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Timber.d(tag + Lifecycle.Event.ON_STOP);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Timber.d(tag + Lifecycle.Event.ON_CREATE);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Timber.d(tag + Lifecycle.Event.ON_DESTROY);
    }
}
