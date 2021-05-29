package dev.iaiabot.thetatrial.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


interface WifiConnect {
    fun connect(context: Context): Flow<State>

    sealed class State {
        object Connected : State()
        object Lost : State()
        class Failure(e: Exception) : State()
    }
}

internal class WifiConnectImpl : WifiConnect {

    companion object {
        private const val WIFI_PASSWORD = "00118118"
        private const val WIFI_SSID = "THETAYP${WIFI_PASSWORD}.OSC"
    }

    override fun connect(context: Context): Flow<WifiConnect.State> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            forceConnectToWifi(context)
        } else {
            forceConnectToWifi()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun forceConnectToWifi(context: Context): Flow<WifiConnect.State> {
        val specifier = WifiNetworkSpecifier.Builder().apply {
            setSsid(WIFI_SSID)
            setWpa2Passphrase(WIFI_PASSWORD)
        }.build()

        val request = NetworkRequest.Builder().apply {
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            setNetworkSpecifier(specifier)
        }.build()

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.d("theta-trial", "on available wifi")
                    manager.bindProcessToNetwork(network)
                    trySend(WifiConnect.State.Connected)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.d("theta-trial", "on lost wifi")
                    trySend(WifiConnect.State.Lost)
                    close()
                }
            }

            manager.requestNetwork(request, callback)
            awaitClose {
                Log.d("theta-trial", "wifi request close")
                cancel()
            }
        }
    }

    private fun forceConnectToWifi(): Flow<WifiConnect.State> = callbackFlow {
        send(WifiConnect.State.Failure(Exception("TODO")))
    }
}
