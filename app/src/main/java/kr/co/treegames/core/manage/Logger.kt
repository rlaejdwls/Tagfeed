package kr.co.treegames.core.manage

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hwang on 2018-10-18.
 *
 * Description :
 */
class Logger {
    companion object {
        /*
         * error log
         */
        fun e(message: String) {
            e("ERROR", message)
        }
        fun e(message: String, tr: Throwable) {
            e("ERROR", message, tr)
        }
        fun e(tag: String, message: String) {
            if (Debugger.DEBUG) Log.e(tag, buildMessage(message))
        }
        fun e(tag: String, message: String, tr: Throwable) {
            if (Debugger.DEBUG) Log.e(tag, buildMessage(message), tr)
        }
        /*
         * warning log
         */
        fun w(message: String) {
            w("WARN", message)
        }
        fun w(tag: String, message: String) {
            if (Debugger.DEBUG) Log.w(tag, buildMessage(message))
        }
        /*
         * Information log
         */
        fun i(message: String) {
            i("INFO", message)
        }
        fun i(message: String, tr: Throwable) {
            i("INFO", message, tr)
        }
        fun i(tag: String, message: String) {
            if (Debugger.DEBUG) Log.i(tag, buildMessage(message))
        }
        fun i(tag: String, message: String, tr: Throwable) {
            if (Debugger.DEBUG) Log.i(tag, buildMessage(message), tr)
        }
        /*
         * debug log
         */
        fun d(message: String) {
            d("DEBUG", message)
        }
        fun d(tag: String, message: String) {
            if (Debugger.DEBUG) Log.d(tag, buildMessage(message))
        }
        /*
         * Verbose log
         */
        fun v(tag: String, message: String) {
            if (Debugger.DEBUG) Log.v(tag, buildMessage(message))
        }
        /**
         * 현재 StackTrace에서 Logger 클래스 정보를 제외하고 그 외 클래스 정보와 메세지를 결합하여 반환하는 함수
         * @param message 로그에 출력하려는 메세지
         * @return 클래스 정보와 사용자가 전달한 메세지
         */
        private fun buildMessage(message: String): String {
            val logger = Logger::class.java.name
            val sb = StringBuilder()

            //현재 시간
            val date = Date(System.currentTimeMillis())
            val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
            val currentDate = dateFormat.format(date)
            sb.append(currentDate).append(":")

            //스택 정보
            val stacks = Thread.currentThread().stackTrace

            for (i in 4 until stacks.size) {
                val element = stacks[i]
                if (element.className.length < logger.length || logger != element.className.substring(0, logger.length)) {
                    sb.append(element.className + "[" + element.methodName + "]" + "(" + element.fileName + ":" + element.lineNumber + "):")
                    break
                }
            }
            sb.append(message)

            return sb.toString()
        }
    }
}