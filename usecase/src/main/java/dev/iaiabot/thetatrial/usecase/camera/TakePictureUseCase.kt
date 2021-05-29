package dev.iaiabot.thetatrial.usecase.camera

import dev.iaiabot.thetatrial.theta.Camera

interface TakePictureUseCase {
    suspend operator fun invoke(): String?
}

internal class TakePictureUseCaseImpl(
    private val camera: Camera
) : TakePictureUseCase {
    override suspend fun invoke(): String? {
        return camera.takePicture()
    }
}
