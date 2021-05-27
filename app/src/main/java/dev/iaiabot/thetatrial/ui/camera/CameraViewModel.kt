package dev.iaiabot.thetatrial.ui.camera

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.iaiabot.thetatrial.theta.Camera
import dev.iaiabot.thetatrial.theta.CameraImpl

abstract class CameraViewModel : ViewModel(), LifecycleObserver {
    abstract val cameraResponse: LiveData<String>
    abstract val imageUrl: LiveData<String>
    abstract val connectedCamera: LiveData<Boolean>

    abstract fun onClickConnectCamera()
    abstract fun onClickTakePicture()
}

internal class CameraViewModelImpl(
) : CameraViewModel() {
    override val cameraResponse = MutableLiveData<String>()
    override val imageUrl = MutableLiveData<String>()
    override val connectedCamera = MutableLiveData<Boolean>(false)

    // TODO: diする
    private val camera: Camera = CameraImpl()

    override fun onClickConnectCamera() {
        camera.connect()
        connectedCamera.value = true
    }

    override fun onClickTakePicture() {
        imageUrl.value = camera.takePicture()
    }
}
