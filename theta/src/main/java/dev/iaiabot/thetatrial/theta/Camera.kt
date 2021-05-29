package dev.iaiabot.thetatrial.theta

import android.content.Context

interface Camera {
    fun connect(context: Context)
    suspend fun takePicture(): String?
}
