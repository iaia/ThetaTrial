package dev.iaiabot.thetatrial.usecase.camera

import android.content.Context
import dev.iaiabot.thetatrial.util.WifiConnect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

interface ConnectCameraUseCase {
    operator fun invoke(context: Context): Flow<Boolean>
}

internal class ConnectCameraUseCaseImpl(
    private val wifiConnect: WifiConnect,
) : ConnectCameraUseCase {

    override fun invoke(context: Context): Flow<Boolean> {
        return wifiConnect.connect(context).mapLatest {
            it == WifiConnect.State.Connected
        }
    }
}
