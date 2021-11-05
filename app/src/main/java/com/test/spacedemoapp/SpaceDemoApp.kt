package com.test.spacedemoapp

import android.app.Application

class SpaceDemoApp: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: SpaceDemoApp
    }
}