package dev.iaiabot.thetatrial.theta

import kotlinx.coroutines.flow.Flow

interface Camera {
    fun takePicture(): Flow<Response>

    sealed class Response {
        class Success(val fileUrl: String) : Response()
        class Failure(val e: TakePictureException) : Response()
        class Other(val message: String) : Response()
    }
}
