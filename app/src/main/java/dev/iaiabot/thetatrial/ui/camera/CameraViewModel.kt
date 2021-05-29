package dev.iaiabot.thetatrial.ui.camera

import android.app.Application
import androidx.lifecycle.*
import dev.iaiabot.thetatrial.theta.Camera
import dev.iaiabot.thetatrial.theta.CameraImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class CameraViewModel(application: Application) : AndroidViewModel(application),
    LifecycleObserver {
    abstract val cameraResponse: LiveData<String>
    abstract val imageUrl: LiveData<String>
    abstract val connectedCamera: LiveData<Boolean>

    abstract fun onClickConnectCamera()
    abstract fun onClickTakePicture()
}

internal class CameraViewModelImpl(
    application: Application
) : CameraViewModel(application) {
    override val cameraResponse = MutableLiveData<String>()
    override val imageUrl = MutableLiveData<String>()
    override val connectedCamera = MutableLiveData<Boolean>(false)

    // TODO: diする
    private val camera: Camera = CameraImpl()

    override fun onClickConnectCamera() {
        viewModelScope.launch(Dispatchers.IO) {
            camera.connect(getApplication())
        }
    }

    override fun onClickTakePicture() {
        // 操作できないようにする progressだす
        viewModelScope.launch(Dispatchers.IO) {
            imageUrl.postValue(camera.takePicture())
        }
    }
}
