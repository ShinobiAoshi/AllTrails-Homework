package com.alltrails.lunch

import android.app.Application
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AllTrailsLunchApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}