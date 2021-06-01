package dev.iaiabot.thetatrial.ui.camera

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dev.iaiabot.thetatrial.R
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
internal class CameraFragmentTest {
    @Test
    fun canClickConnectCameraButton() {
        val scenario = launchFragmentInContainer<CameraFragment>(
            factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return CameraFragment()
                }
            }, themeResId = R.style.Theme_MaterialComponents_NoActionBar
        )
        onView(withId(R.id.mb_connect_camera))
            .perform(click())
            .check(matches(isDisplayed()))
    }
}
