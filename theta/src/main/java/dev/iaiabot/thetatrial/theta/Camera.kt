package dev.iaiabot.thetatrial.theta

interface Camera {
    suspend fun takePicture(): String?
}
