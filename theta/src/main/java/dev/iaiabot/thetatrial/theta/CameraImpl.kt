package dev.iaiabot.thetatrial.theta

import dev.iaiabot.thetatrial.theta.network.HttpConnector
import dev.iaiabot.thetatrial.theta.network.HttpEventListener
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class CameraImpl : Camera {

    companion object {
        private const val THETA_IP_ADDRESS = "192.168.1.1"
    }

    private val camera: HttpConnector = HttpConnector(THETA_IP_ADDRESS)

    override suspend fun takePicture(): String? {
        return suspendCoroutine { continuation ->
            camera.takePicture(
                object : HttpEventListener {
                    override fun onCheckStatus(newStatus: Boolean) {
                    }

                    override fun onObjectChanged(latestCapturedFileId: String?) {
                        continuation.resume(latestCapturedFileId)
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(errorMessage: String?) {
                        continuation.resumeWithException(
                            TakePictureException(errorMessage ?: "error")
                        )
                    }
                }
            )
        }
    }
}
