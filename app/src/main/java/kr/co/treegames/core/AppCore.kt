package kr.co.treegames.core

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
//import android.arch.lifecycle.Lifecycle
//import android.arch.lifecycle.LifecycleObserver
//import android.arch.lifecycle.OnLifecycleEvent
//import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import kr.co.treegames.core.manage.Debugger
import kr.co.treegames.core.manage.ExceptionHandler

/**
 * Created by Hwang on 2018-09-04.
 *
 * Description :
 */
@SuppressLint("Registered")
open class AppCore: Application(), LifecycleObserver {
    companion object {
        private lateinit var appCore: AppCore
    }

    private val point = Point()
    private var density: Float = 0.toFloat()
    private var statusBarHeight: Int = 0

    override fun onCreate() {
        super.onCreate()
        appCore = this
        initApplication()
        listenForForeground()
    }
    private fun initApplication() {
        //어플리케이션 생명 주기
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        //디버거 초기화
        Debugger().initialize(this)
        //전역 예외 핸들러 선언
        if (Debugger.DEBUG) {
            ExceptionHandler(this)
        }
        //전역 정보
        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getSize(point)
        display.getMetrics(outMetrics)
        density = outMetrics.density

        statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
    }
    private fun listenForForeground() {
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                appCore.onActivityCreated(activity, savedInstanceState)
            }
            override fun onActivityStarted(activity: Activity) {
                appCore.onActivityStarted(activity)
            }
            override fun onActivityResumed(activity: Activity) {
                appCore.onActivityResumed(activity)
            }
            override fun onActivityPaused(activity: Activity) {
                appCore.onActivityPaused(activity)
            }
            override fun onActivityStopped(activity: Activity) {
                appCore.onActivityStopped(activity)
            }
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                appCore.onActivitySaveInstanceState(activity, outState)
            }
            override fun onActivityDestroyed(activity: Activity) {
                appCore.onActivityDestroyed(activity)
            }
        })
    }

    protected open fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    protected open fun onActivityStarted(activity: Activity) {}
    protected open fun onActivityResumed(activity: Activity) {}
    protected open fun onActivityPaused(activity: Activity) {}
    protected open fun onActivityStopped(activity: Activity) {}
    protected open fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    protected open fun onActivityDestroyed(activity: Activity) {}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun notifyForeground() {
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun notifyBackground() {
    }

    fun getScreenWidth(): Int {
        return appCore.point.x
    }
    fun getScreenHeight(): Int {
        return appCore.point.y
    }
    fun getDensity(): Float {
        return appCore.density
    }
    fun getStatusBarHeight(): Int {
        return appCore.statusBarHeight
    }
    fun dpToPx(dp: Float): Int {
        return (dp * appCore.density + 0.5f).toInt()
    }
}