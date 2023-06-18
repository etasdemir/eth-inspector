package com.etasdemir.ethinspector

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Locale

@HiltAndroidApp
class EthInspectorApp: Application() {

    val systemLanguage: String = Locale.getDefault().language
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}