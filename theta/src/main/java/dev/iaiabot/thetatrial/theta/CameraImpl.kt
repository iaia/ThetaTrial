package dev.iaiabot.thetatrial.theta

class CameraImpl : Camera {
    lateinit var detectedDevices: List<CameraDevice>

    private fun detect() {
        detectedDevices = CameraDeviceDetector.detect(DeviceInterface.WLAN)
    }

    override fun connect() {
        detect()
        // TODO: ない場合はエラーを返す
        val cameraDevice = detectedDevices.firstOrNull() ?: return
        // TODO: responseを形を変えて返す
        val response = cameraDevice.connect(DeviceInterface.WLAN)
    }

    override fun takePicture(): String? {
        return null
    }
}
