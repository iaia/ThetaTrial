package dev.iaiabot.thetatrial.ui.camera.di

import dev.iaiabot.thetatrial.ui.camera.CameraViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Module {
    val appModule = module {
        viewModel<CameraViewModel> { CameraViewModel() }
    }
}
