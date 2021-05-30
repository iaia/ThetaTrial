package dev.iaiabot.thetatrial.usecase.camera

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.thetatrial.theta.Camera
import dev.iaiabot.thetatrial.theta.TakePictureException
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope

internal class TakePictureUseCaseImplTest : DescribeSpec({
    lateinit var useCase: TakePictureUseCase
    lateinit var camera: Camera
    val dispatcher = Dispatchers.Unconfined

    instantTaskExecutorRule()

    describe("#invoke") {
        val coroutineScope = TestCoroutineScope()
        lateinit var responseFlow: Flow<Camera.Response>

        beforeTest {
            camera = mockk()
            useCase = TakePictureUseCaseImpl(camera, dispatcher)
        }

        context("Success") {
            beforeTest {
                responseFlow = flow {
                    emit(Camera.Response.Success("FILE_URL"))
                }
                every { camera.takePicture() } returns responseFlow
            }

            it("should return file url") {
                useCase(coroutineScope)
                assertThat(useCase.fileUrl.value).isEqualTo("FILE_URL")
            }
        }

        context("Failure") {
            beforeTest {
                responseFlow = flow {
                    emit(Camera.Response.Failure(TakePictureException("test error")))
                }
                every { camera.takePicture() } returns responseFlow
            }

            it("should return error message") {
                runBlocking {
                    useCase(coroutineScope)
                    val response = useCase.response.replayCache.first()
                    assertThat(response).isEqualTo("test error")
                }
            }
        }

        context("Other") {
            beforeTest {
                responseFlow = flow {
                    emit(Camera.Response.Other("shooting"))
                }
                every { camera.takePicture() } returns responseFlow
            }

            it("should return message") {
                runBlocking {
                    useCase(coroutineScope)
                    val response = useCase.response.replayCache.first()
                    assertThat(response).isEqualTo("shooting")
                }
            }
        }
    }
})
