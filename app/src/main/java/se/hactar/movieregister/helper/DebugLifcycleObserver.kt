package se.hactar.movieregister.helper

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent

import timber.log.Timber

class DebugLifcycleObserver(lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    private val tag: String = "<" + lifecycleOwner.javaClass.simpleName + "> "

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() = Timber.d(tag + Lifecycle.Event.ON_RESUME)

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() = Timber.d(tag + Lifecycle.Event.ON_PAUSE)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() = Timber.d(tag + Lifecycle.Event.ON_START)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() = Timber.d(tag + Lifecycle.Event.ON_STOP)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() = Timber.d(tag + Lifecycle.Event.ON_CREATE)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = Timber.d(tag + Lifecycle.Event.ON_DESTROY)
}
