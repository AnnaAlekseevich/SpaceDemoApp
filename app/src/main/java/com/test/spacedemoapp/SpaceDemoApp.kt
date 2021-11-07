package com.test.spacedemoapp

import android.app.Application
import com.test.spacedemoapp.domain.dagger.AppModule

class SpaceDemoApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        this.appComponent = this.initDagger()
    }

    companion object {
        lateinit var INSTANCE: SpaceDemoApp
    }

    private fun initDagger() = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()
}