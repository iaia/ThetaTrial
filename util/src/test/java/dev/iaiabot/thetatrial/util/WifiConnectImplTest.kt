package dev.iaiabot.thetatrial.util

import android.content.Context
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk

internal class WifiConnectImplTest : DescribeSpec({
    val wifiConnect: WifiConnect = WifiConnectImpl()

    // mockが大量に必要でめんどくさいので一旦やらない
    xdescribe("#connect") {
        val context: Context = mockk()

        it("") {
            wifiConnect.connect(context)
        }
    }
})
