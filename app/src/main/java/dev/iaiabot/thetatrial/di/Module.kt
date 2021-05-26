package dev.iaiabot.thetatrial.di

import dev.iaiabot.thetatrial.ui.camera.CameraViewModel
import dev.iaiabot.thetatrial.ui.camera.CameraViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val appModule = module {
        viewModel<CameraViewModel> { CameraViewModelImpl() }
    }
}
