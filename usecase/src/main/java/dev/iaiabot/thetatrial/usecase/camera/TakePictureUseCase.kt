package dev.iaiabot.thetatrial.usecase.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.iaiabot.thetatrial.theta.Camera
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface TakePictureUseCase {
    val fileUrl: LiveData<String?>
    val response: SharedFlow<String?>

    operator fun invoke(
        coroutineScope: CoroutineScope,
    )
}

internal class TakePictureUseCaseImpl(
    private val camera: Camera,
    private val dispatcher: CoroutineDispatcher,
) : TakePictureUseCase {

    override val fileUrl = MutableLiveData<String?>(null)
    override val response = MutableSharedFlow<String?>()

    override fun invoke(
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch(dispatcher) {
            camera.takePicture().onEach {
                when (it) {
                    is Camera.Response.Success -> {
                        fileUrl.postValue(it.fileUrl)
                    }
                    is Camera.Response.Failure -> {
                        fileUrl.postValue(null)
                        response.emit(it.e.message)
                    }
                    is Camera.Response.Other -> {
                        fileUrl.postValue(null)
                        response.emit(it.message)
                    }
                }
            }.collect()
        }
    }
}
