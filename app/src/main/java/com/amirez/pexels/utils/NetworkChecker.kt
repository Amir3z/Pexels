package com.amirez.pexels.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkChecker(private val context: Context) {

    val isConnected: Boolean
        get() {

            var result = false

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val network =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

                result = when {
                    network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }

            } else {

                result = when(connectivityManager.activeNetworkInfo?.type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE-> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }

            return result
        }

}