package dev.iaiabot.thetatrial.usecase.camera

import android.content.Context
import com.google.common.truth.Truth.assertThat
import dev.iaiabot.thetatrial.util.WifiConnect
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

internal class ConnectCameraUseCaseImplTest : DescribeSpec({
    lateinit var useCase: ConnectCameraUseCase
    lateinit var wifiConnect: WifiConnect
    lateinit var stateFlow: Flow<WifiConnect.State>

    describe("#invoke") {
        lateinit var context: Context

        beforeTest {
            context = mockk()
            wifiConnect = mockk()
            useCase = ConnectCameraUseCaseImpl(wifiConnect)
        }

        describe("Connected") {
            beforeEach {
                stateFlow = flow {
                    emit(WifiConnect.State.Connected)
                }
                every { wifiConnect.connect(any()) } returns stateFlow
            }

            it("should return true") {
                val result = useCase(context)

                assertThat(result.single()).isTrue()
            }
        }

        describe("Failure") {
            beforeEach {
                stateFlow = flow {
                    emit(WifiConnect.State.Failure(Exception("test error")))
                }
                every { wifiConnect.connect(any()) } returns stateFlow
            }

            it("should return false") {
                val result = useCase(context)

                assertThat(result.single()).isFalse()
            }
        }

        describe("Lost") {
            beforeEach {
                stateFlow = flow {
                    emit(WifiConnect.State.Lost)
                }
                every { wifiConnect.connect(any()) } returns stateFlow
            }

            it("should return false") {
                val result = useCase(context)

                assertThat(result.single()).isFalse()
            }
        }
    }
})
