package kr.co.treegames.tagfeed.manage.runner

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description : 풀링 쓰레드
 */
class PoolRunner(
        val local: Executor = Executors.newFixedThreadPool(5),
        val main: Executor = MainThreadExecutor()
) {
    class MainThreadExecutor: Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) { mainThreadHandler.post(command) }
    }
}