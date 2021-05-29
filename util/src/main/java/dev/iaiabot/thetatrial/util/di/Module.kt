package dev.iaiabot.thetatrial.util.di

import dev.iaiabot.thetatrial.util.WifiConnect
import dev.iaiabot.thetatrial.util.WifiConnectImpl
import org.koin.dsl.module

object Module {
    val utilModule = module {
        single<WifiConnect> { WifiConnectImpl() }
    }
}
