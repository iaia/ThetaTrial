package dev.iaiabot.thetatrial.theta.di

import dev.iaiabot.thetatrial.theta.Camera
import dev.iaiabot.thetatrial.theta.CameraImpl
import dev.iaiabot.thetatrial.theta.network.HttpConnector
import org.koin.dsl.module

object Module {
    private const val THETA_IP_ADDRESS = "192.168.1.1"

    val thetaModule = module {
        single<Camera> { CameraImpl(get()) }

        single { HttpConnector(THETA_IP_ADDRESS) }
    }
}
