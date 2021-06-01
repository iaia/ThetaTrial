package dev.iaiabot.thetatrial.ui.camera

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
internal class CameraFragmentTest {

    companion object {
        private const val PACKAGE = "dev.iaiabot.thetatrial"
        private const val LAUNCH_TIMEOUT = 5000L
    }

    // WiFi接続のダイアログがespressoで処理できないのでUiAutomatorを使う
    @Test
    fun canClickConnectCameraButton() {
        // TODO: そもそもカメラの電源をONにしておかないといけない
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT
        )

        // Launch the app
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(PACKAGE)
        context.startActivity(intent)

        // TODO: screenshot撮る
        device.findObject(
            UiSelector().text("カメラに接続")
        ).click()
        // CI上などでは日本語ではない可能性がある
        device.findObject(
            UiSelector().text("接続")
        ).click()
        device.findObject(
            UiSelector().text("写真撮影")
        ).click()
        /**
         * TODO: 撮影が完了したことを検証する (やり方も考える)
         * 「画像が表示されている」をUiAutomatorだと確認しづらい(?)
         * 画像を取得するまで時間がかかるのでコケる可能性高い
         * ので別のやり方を考える
         *
         * - instrumentation testだけで完結しないものとして考える場合
         *   - thetaの中に画像が増えていることを確認する
         *   - 夜中に動かして次の日確認するとか
         *   - thetaに繋ぎっぱなしのPCで、ポーリングして前回との差分があるか確認するとか
         * - 画像が表示されているっていうのを検証しづらいとして考える場合
         *   - debugBuild時のみ画像のURLを表示できるViewを置いておく
         *   - URLが表示されていることを確認する (http://192.168.11.1/xxxx の正規表現)
         */
    }
}
