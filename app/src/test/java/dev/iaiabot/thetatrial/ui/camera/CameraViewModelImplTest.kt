package dev.iaiabot.thetatrial.ui.camera

import android.app.Application
import com.google.common.truth.Truth.assertThat
import dev.iaiabot.thetatrial.InstantTaskExecutorRule
import dev.iaiabot.thetatrial.usecase.camera.ConnectCameraUseCase
import dev.iaiabot.thetatrial.usecase.camera.TakePictureUseCase
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CameraViewModelImplTest : DescribeSpec({
    lateinit var viewModel: CameraViewModel
    lateinit var connectCameraUseCase: ConnectCameraUseCase
    lateinit var takePictureUseCase: TakePictureUseCase
    val application: Application = mockk()
    lateinit var connectedFlow: Flow<Boolean>

    listener(InstantTaskExecutorRule())

    describe("#onClickConnectCamera") {
        beforeTest {
            connectCameraUseCase = mockk() {
                coEvery { this@mockk.invoke(any()) } answers { connectedFlow }
            }
            takePictureUseCase = mockk() {
                every { fileUrl } returns mockk()
            }
            viewModel = CameraViewModelImpl(application, connectCameraUseCase, takePictureUseCase)
        }

        context("when not return") {
            beforeEach {
                connectedFlow = flow { }
            }

            it("should call connectCameraUseCase") {
                viewModel.onClickConnectCamera()
                coVerify { connectCameraUseCase.invoke(application) }
            }
        }


        context("when return true from flow") {
            beforeEach {
                connectedFlow = flow { emit(true) }
            }

            it("should connectedCamera is true") {
                viewModel.onClickConnectCamera()
                assertThat(viewModel.connectedCamera.value).isTrue()
            }
        }

        context("when return false from flow") {
            beforeEach {
                connectedFlow = flow { emit(false) }
            }

            it("should connectedCamera is false") {
                viewModel.onClickConnectCamera()
                assertThat(viewModel.connectedCamera.value).isFalse()
            }
        }
    }
})
