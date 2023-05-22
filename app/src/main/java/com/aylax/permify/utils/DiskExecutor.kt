package com.aylax.permify.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DiskExecutor() : Executor {
    private val executor: Executor

    init {
        executor = Executors.newSingleThreadExecutor()
    }

    override fun execute(command: Runnable) {
        executor.execute(command)
    }
}