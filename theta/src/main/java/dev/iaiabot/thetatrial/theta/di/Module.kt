package dev.iaiabot.thetatrial.theta.di

import dev.iaiabot.thetatrial.theta.Camera
import dev.iaiabot.thetatrial.theta.CameraImpl
import org.koin.dsl.module

object Module {
    val thetaModule = module {
        single<Camera> { CameraImpl() }
    }
}
