package com.test.spacedemoapp.domain.net.networkconnection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.test.spacedemoapp.SpaceDemoApp

class ConnectivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("ConnectivityReceiver", "onReceive")
        //context?.let { SpaceDemoApp.updateInternetState(isConnectedOrConnecting(it)) }

    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
        Log.d("ConnectivityReceiver", "isConnectedOrConnecting")
    }


}