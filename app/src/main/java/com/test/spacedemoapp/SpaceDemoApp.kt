package com.test.spacedemoapp

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.os.Build
import com.test.spacedemoapp.domain.net.networkconnection.NetWorkConnection
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.subjects.BehaviorSubject

@HiltAndroidApp
class SpaceDemoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        NetWorkConnection.isInternetAvailable(this)
        internetStateObservable.onNext(NetWorkConnection.isInternetAvailable(this))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallback = createNetworkCallback()
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            val intentFilter = IntentFilter(CONNECTIVITY_ACTION)
            val broadcastReceiver = createBroadcastReceiver()
            registerReceiver(broadcastReceiver, intentFilter)
        }

    }

    private fun createBroadcastReceiver() = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val isNoConnectivity = intent?.extras?.getBoolean(EXTRA_NO_CONNECTIVITY) ?: true
            internetStateObservable.onNext(!isNoConnectivity)
        }

    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            val isInternet = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)
            val isValidated = networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED)
            internetStateObservable.onNext(isInternet && isValidated)
        }

        override fun onLost(network: Network) {
            internetStateObservable.onNext(false)
        }
    }

    companion object {
        lateinit var INSTANCE: SpaceDemoApp
        val internetStateObservable: BehaviorSubject<Boolean> = BehaviorSubject.create()

        fun updateInternetState(isInternetAvailable: Boolean) {
            internetStateObservable.onNext(isInternetAvailable)
        }
    }
}