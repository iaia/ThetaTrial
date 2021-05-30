package dependencies

object Deps {
    val kotlin_version = "1.5.10"

    object Kotlin {
        val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"

        val coroutine_version = "1.5.0"

        val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version"
        object Test {
            val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutine_version"
        }
    }

    object AndroidX {
        val coreKtx = "androidx.core:core-ktx:1.3.2"
        val appCompat = "androidx.appcompat:appcompat:1.2.0"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"

        val nav_version = "2.3.5"
        val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$nav_version"
        val navigationUi = "androidx.navigation:navigation-ui-ktx:$nav_version"

        val liveDataLifecycleKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"

        object Test {
            val coreTesting = "androidx.arch.core:core-testing:2.1.0"
            val extJunit = "androidx.test.ext:junit:1.1.2"
            val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }

    object MaterialDesign {
        val material = "com.google.android.material:material:1.3.0"
    }

    object Koin {
        private val koin_version = "3.0.2"
        val base = "io.insert-koin:koin-android:$koin_version"
        val ext = "io.insert-koin:koin-android-ext:$koin_version"
    }

    object Test {
        val junit4 = "junit:junit:4.13.2"
        private val mockk_version = "1.11.0"
        val mockk = "io.mockk:mockk:$mockk_version"
        val mockkAgent = "io.mockk:mockk-agent-jvm:$mockk_version"
        val truth = "com.google.truth:truth:1.1"
        val kotest = "io.kotest:kotest-runner-junit5:4.6.0"
    }

    val picasso = "com.squareup.picasso:picasso:2.71828"
}
