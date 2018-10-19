package kr.co.treegames.tagfeed.manage.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import kr.co.treegames.tagfeed.App
import kr.co.treegames.tagfeed.BuildConfig

/**
 * Created by Hwang on 2018-09-04.
 *
 * Description : 파이어베이스의 이벤트 전송 클래스
 */
class Analyzer(private val builder: Builder) {
    companion object {
        fun with(event: String, action: Builder.() -> Unit): Builder {
            return with(App.get().applicationContext, event, action)
        }
        fun with(context: Context, event: String, action: Builder.() -> Unit): Builder {
            return Builder(context, event).apply(action)
        }
    }
    fun event() {
        if (BuildConfig.ANALYTICS || builder.isDebugSend) {
            if (builder.event == "") {
                return
            }
            FirebaseAnalytics.getInstance(builder.context)
                    .logEvent("TESTEvent" + builder.event, builder.param)
        }
    }
    class Builder(internal val context: Context, internal val event: String,
                  var param: Bundle = Bundle(), var isDebugSend: Boolean = false) {
        fun build() = Analyzer(this@Builder)
        fun event() = Analyzer(this@Builder).event()
    }
}