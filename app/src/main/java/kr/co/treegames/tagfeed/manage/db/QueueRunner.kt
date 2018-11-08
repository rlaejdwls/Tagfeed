package kr.co.treegames.tagfeed.manage.db

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
class QueueRunner {
    private val runnerCount: Int = 3

    private val local: Executor
    private val main: Executor

    init {
        local = Executors.newFixedThreadPool(runnerCount)
        main = MainThreadExecutor()
    }

    fun local(): Executor = local
    fun main(): Executor = main

    class MainThreadExecutor: Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) { mainThreadHandler.post(command) }
    }
}