package com.yumodev.arch

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Created by yumodev on 17/12/6.
 */
class ArchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}