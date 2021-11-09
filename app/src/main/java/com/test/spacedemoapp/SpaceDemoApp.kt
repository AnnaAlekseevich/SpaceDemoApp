package com.test.spacedemoapp

import android.app.Application
import android.util.Log
import com.test.spacedemoapp.domain.dagger.AppModule
import com.test.spacedemoapp.domain.net.networkconnection.NetWorkConnection
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class SpaceDemoApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Log.d("INTERNETCONNECTION", "check = ${NetWorkConnection.isInternetAvailable(this)}")
        NetWorkConnection.isInternetAvailable(this)
        internetStateObservable.onNext(NetWorkConnection.isInternetAvailable(this))
        this.appComponent = this.initDagger()

    }

    companion object {
        lateinit var INSTANCE: SpaceDemoApp
        private val internetStateObservable: BehaviorSubject<Boolean> = BehaviorSubject.create()

        fun updateInternetState(isInternetAvailable: Boolean) {
            internetStateObservable.onNext(isInternetAvailable)
        }
    }

    private fun initDagger() = DaggerAppComponent.builder()
        .appModule(AppModule(this, internetStateObservable))
        .build()
}