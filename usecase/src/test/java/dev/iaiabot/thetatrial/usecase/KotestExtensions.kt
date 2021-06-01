package dev.iaiabot.thetatrial.usecase

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.kotest.core.spec.style.DescribeSpec

// TODO: testmodule 作って移す
internal fun DescribeSpec.instantTaskExecutorRule() {
    beforeTest {
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

    afterTest {
    }
}
