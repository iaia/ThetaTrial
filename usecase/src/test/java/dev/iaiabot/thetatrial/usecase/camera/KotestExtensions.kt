package dev.iaiabot.thetatrial.usecase.camera

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.kotest.core.spec.style.DescribeSpec


fun DescribeSpec.instantTaskExecutorRule() {
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
