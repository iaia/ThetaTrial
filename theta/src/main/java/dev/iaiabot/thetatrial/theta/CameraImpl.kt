package dev.iaiabot.thetatrial.theta

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import androidx.annotation.RequiresApi
import dev.iaiabot.thetatrial.theta.network.HttpConnector
import dev.iaiabot.thetatrial.theta.network.HttpEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class CameraImpl : Camera {
    var camera: HttpConnector? = null

    companion object {
        private const val WIFI_PASSWORD = "00118118"
        private const val WIFI_SSID = "THETAYP${WIFI_PASSWORD}.OSC"
    }

    override fun connect(context: Context) {
        forceConnectToWifi(context)
    }

    override suspend fun takePicture(): String? {
        return suspendCoroutine<String?> { continuation ->
            val shootResult = camera?.takePicture(
                object : HttpEventListener {
                    override fun onCheckStatus(newStatus: Boolean) {
                        // TODO("Not yet implemented")
                    }

                    override fun onObjectChanged(latestCapturedFileId: String?) {
                        continuation.resume(latestCapturedFileId)
                    }

                    override fun onCompleted() {
                        // TODO("Not yet implemented")
                    }

                    override fun onError(errorMessage: String?) {
                        // TODO("Not yet implemented")
                    }
                }
            )
        }
    }

    // util moduleに持っていく
    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("MissingPermission")
    private fun forceConnectToWifi(context: Context) {
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
        manager.requestNetwork(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                camera = HttpConnector("192.168.1.1")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                camera = null
            }
        })
    }
}
