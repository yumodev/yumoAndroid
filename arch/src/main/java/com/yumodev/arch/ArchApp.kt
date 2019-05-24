package com.yumodev.arch

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

/**
 * Created by yumodev on 17/12/6.
 */
class ArchApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}