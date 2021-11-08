package com.example.practice

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Luis hernandez on 11/3/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */

@HiltAndroidApp
class PracticeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}