package dev.iaiabot.thetatrial.ui.camera

import android.app.Application
import androidx.lifecycle.*
import dev.iaiabot.thetatrial.usecase.camera.ConnectCameraUseCase
import dev.iaiabot.thetatrial.usecase.camera.TakePictureUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class CameraViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {
    abstract val cameraResponse: LiveData<String>
    abstract val imageUrl: LiveData<String?>
    abstract val connectedCamera: LiveData<Boolean>

    abstract fun onClickConnectCamera()
    abstract fun onClickTakePicture()
}

internal class CameraViewModelImpl(
    application: Application,
    private val connectCameraUseCase: ConnectCameraUseCase,
    private val takePictureUseCase: TakePictureUseCase,
) : CameraViewModel(application) {
    override val cameraResponse = MutableLiveData<String>()
    override val imageUrl = takePictureUseCase.fileUrl
    override val connectedCamera = MutableLiveData(false)

    override fun onClickConnectCamera() {
        viewModelScope.launch {
            connectCameraUseCase(getApplication()).collect {
                connectedCamera.postValue(it)
            }
        }
    }

    override fun onClickTakePicture() {
        viewModelScope.launch {
            takePictureUseCase.response.collect {
                cameraResponse.postValue(it)
            }
        }
        takePictureUseCase(viewModelScope)
    }
}
