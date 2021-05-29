package dev.iaiabot.thetatrial.theta

import android.content.Context

interface Camera {
    suspend fun getSerialNumber(): String?
    fun connect(context: Context)
    suspend fun takePicture(): String?
}
