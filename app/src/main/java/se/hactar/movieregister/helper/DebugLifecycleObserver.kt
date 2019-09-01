package se.hactar.movieregister.helper

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent

import timber.log.Timber

class DebugLifecycleObserver(lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    private val tag: String = "<" + lifecycleOwner.javaClass.simpleName + "> "

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() = Timber.d(tag + Lifecycle.Event.ON_RESUME)

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() = Timber.d(tag + Lifecycle.Event.ON_PAUSE)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() = Timber.d(tag + Lifecycle.Event.ON_START)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() = Timber.d(tag + Lifecycle.Event.ON_STOP)

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() = Timber.d(tag + Lifecycle.Event.ON_CREATE)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() = Timber.d(tag + Lifecycle.Event.ON_DESTROY)
}
