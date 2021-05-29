package dev.iaiabot.thetatrial.usecase.di

import dev.iaiabot.thetatrial.theta.di.Module.thetaModule
import dev.iaiabot.thetatrial.usecase.camera.ConnectCameraUseCase
import dev.iaiabot.thetatrial.usecase.camera.ConnectCameraUseCaseImpl
import dev.iaiabot.thetatrial.usecase.camera.TakePictureUseCase
import dev.iaiabot.thetatrial.usecase.camera.TakePictureUseCaseImpl
import dev.iaiabot.thetatrial.util.di.Module.utilModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object Module {
    val useCaseModule = module {
        loadKoinModules(utilModule)
        loadKoinModules(thetaModule)

        single<ConnectCameraUseCase> { ConnectCameraUseCaseImpl(get()) }
        factory<TakePictureUseCase> { TakePictureUseCaseImpl(get()) }
    }
}
