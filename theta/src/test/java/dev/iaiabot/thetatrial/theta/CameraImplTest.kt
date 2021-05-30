package dev.iaiabot.thetatrial.theta

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.thetatrial.theta.network.HttpConnector
import dev.iaiabot.thetatrial.theta.network.HttpEventListener
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

internal class CameraImplTest : DescribeSpec({
    lateinit var camera: Camera
    lateinit var httpConnector: HttpConnector
    lateinit var httpEventListener: HttpEventListener

    describe("#takePicture") {
        lateinit var latch: CountDownLatch

        beforeEach {
            latch = CountDownLatch(1)
            httpConnector = mockk() {
                every { takePicture(any()) } answers {
                    httpEventListener = this.args.first() as HttpEventListener
                    latch.countDown()
                    mockk(relaxed = true) {
                        every { name } returns "shoot result"
                    }
                }
            }
            camera = CameraImpl(httpConnector)
        }

        it("should return shoot result") {
            val flow = camera.takePicture()
            var results: List<Camera.Response>? = null

            val job = launch(Dispatchers.Unconfined) {
                results = flow.toList()
            }

            latch.await(2, TimeUnit.SECONDS)
            httpEventListener.onCompleted()
            job.join()
            with(results?.get(0)) {
                assertThat(this).isInstanceOf(Camera.Response.Other::class.java)
                assertThat((this as Camera.Response.Other).message).isEqualTo("shoot result")
            }
        }

        it("should return completed response") {
            val flow = camera.takePicture()
            var results: List<Camera.Response>? = null

            val job = launch(Dispatchers.Unconfined) {
                results = flow.toList()
            }

            latch.await(2, TimeUnit.SECONDS)
            httpEventListener.onCompleted()
            job.join()
            with(results?.get(1)) {
                assertThat(this).isInstanceOf(Camera.Response.Other::class.java)
                assertThat((this as Camera.Response.Other).message).isEqualTo("completed")
            }
        }

        it("should return error response") {
            val flow = camera.takePicture()
            var results: String? = null

            val job = launch(Dispatchers.Unconfined) {
                try {
                    flow.toList()
                } catch (e: CancellationException) {
                    results = e.message
                }
            }

            latch.await(2, TimeUnit.SECONDS)
            httpEventListener.onError("test error")
            job.join()
            assertThat(results).isEqualTo("test error")
        }


        it("should return file url") {
            val flow = camera.takePicture()
            var results: List<Camera.Response>? = null

            val job = launch(Dispatchers.Unconfined) {
                results = flow.toList()
            }

            latch.await(2, TimeUnit.SECONDS)
            httpEventListener.onObjectChanged("http://example.com/file_url")
            httpEventListener.onCompleted()
            job.join()
            with(results?.get(1)) {
                assertThat(this).isInstanceOf(Camera.Response.Success::class.java)
                assertThat((this as Camera.Response.Success).fileUrl).isEqualTo("http://example.com/file_url")
            }
        }
    }
})
