package com.ardroid.gitrepo.dataSources.data

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

object CheckNetwork {

    fun isConnected(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
       Log.d("SUPER_TAG", "hasNetworkAvailable: ${(network != null)}")
        return (network?.isConnected) ?: false
    }
}