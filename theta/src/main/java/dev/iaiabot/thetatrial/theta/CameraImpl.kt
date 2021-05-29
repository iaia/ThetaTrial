package dev.iaiabot.thetatrial.theta

import dev.iaiabot.thetatrial.theta.network.HttpConnector
import dev.iaiabot.thetatrial.theta.network.HttpEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class CameraImpl : Camera {

    companion object {
        private const val THETA_IP_ADDRESS = "192.168.1.1"
    }

    private val camera: HttpConnector = HttpConnector(THETA_IP_ADDRESS)

    override suspend fun takePicture(): Flow<Camera.Response> {
        return callbackFlow<Camera.Response> {
            val shootResult = camera.takePicture(
                object : HttpEventListener {
                    override fun onCheckStatus(newStatus: Boolean) {
                    }

                    override fun onObjectChanged(latestCapturedFileId: String?) {
                        latestCapturedFileId?.let {
                            trySend(Camera.Response.Success(it))
                        }
                            ?: trySend(Camera.Response.Failure(TakePictureException("File Not Found")))
                    }

                    override fun onCompleted() {
                        Camera.Response.Other("completed")
                        this@callbackFlow.close()
                    }

                    override fun onError(errorMessage: String?) {
                        val message = errorMessage ?: "unknown error"
                        cancel(message, TakePictureException(message))
                    }
                }
            )
            send(Camera.Response.Other(shootResult.name))

            awaitClose {
                cancel()
            }
        }
    }
}
