package dev.iaiabot.thetatrial.ui.camera

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class CameraViewModel : ViewModel(), LifecycleObserver {
    abstract val cameraResponse: LiveData<String>
    abstract fun onClickConnectCamera()
    abstract fun onClickTakePicture()
}

internal class CameraViewModelImpl(
) : CameraViewModel() {
    override val cameraResponse = MutableLiveData<String>()

    override fun onClickConnectCamera() {
    }

    override fun onClickTakePicture() {
    }
}
