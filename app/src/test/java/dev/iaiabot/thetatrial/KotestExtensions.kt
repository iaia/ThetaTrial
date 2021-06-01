package dev.iaiabot.thetatrial

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.kotest.core.listeners.TestListener
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

// TODO: testmodule 作って移す
@ExperimentalCoroutinesApi
internal class InstantTaskExecutorRule() : TestListener {
    private val dispatcher = TestCoroutineDispatcher()

    override suspend fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        Dispatchers.setMain(dispatcher)
        ArchTaskExecutor
            .getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) {
                    runnable.run()
                }

                override fun isMainThread(): Boolean {
                    return true
                }

                override fun postToMainThread(runnable: Runnable) {
                    runnable.run()
                }
            })
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        Dispatchers.resetMain()
    }
}
