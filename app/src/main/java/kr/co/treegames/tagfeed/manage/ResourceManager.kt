package kr.co.treegames.tagfeed.manage

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import kr.co.treegames.tagfeed.R

/**
 * Created by Hwang on 2018-10-23.
 *
 * Description : 프레젠터에서 리소스를 과연 어떻게 공급할 것인가에 대한 고민
 * 그리고 과연 이렇게까지 해서 프레젠터의 무결함을 지켜줄 필요성이 있느냐에 대한 의문이 듬
 */
class ResourceManager(private val context: Context) {
    fun getString(resString: String): String {
        return context.getString(context.resources.getIdentifier(resString, "string", context.packageName))
    }
    fun getColor(resString: String): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(context.resources.getIdentifier(resString, "color", context.packageName), null)
        } else {
            context.resources.getColor(context.resources.getIdentifier(resString, "color", context.packageName))
        }
    }
    fun getDimension(resString: String) {
        context.resources.getIdentifier(resString, "dimen", context.packageName)
    }
    fun getDrawable(resString: String): Drawable {
        return context.resources.getDrawable(context.resources.getIdentifier(resString, "drawable", context.packageName), null)
    }
}