package dev.iaiabot.thetatrial.di

import android.app.Application
import dev.iaiabot.thetatrial.ui.camera.CameraViewModel
import dev.iaiabot.thetatrial.ui.camera.CameraViewModelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val appModule = module {
        viewModel<CameraViewModel> { CameraViewModelImpl(androidContext() as Application) }
    }
}
