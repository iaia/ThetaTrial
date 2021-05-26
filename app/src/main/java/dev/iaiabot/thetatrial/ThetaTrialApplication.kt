package dev.iaiabot.thetatrial

import android.app.Application
import dev.iaiabot.thetatrial.di.Module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ThetaTrialApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ThetaTrialApplication)
            modules(listOf(appModule))
        }
    }
}
