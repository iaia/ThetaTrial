package dev.iaiabot.thetatrial.util

import android.content.Context
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk

internal class WifiConnectImplTest : DescribeSpec({
    val wifiConnect: WifiConnect = WifiConnectImpl()

    xdescribe("#connect") {
        val context: Context = mockk()

        it("") {
            wifiConnect.connect(context)
        }
    }
})
