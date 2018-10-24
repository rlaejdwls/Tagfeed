package kr.co.treegames.tagfeed.manage

import android.content.Context
import kr.co.treegames.tagfeed.data.model.Regex

/**
 * Created by Hwang on 2018-10-23.
 *
 * Description : 유틸리티
 */
class Utilities(private val context: Context) {
    /**
     * fun isEmail, fun checkPassword를 Extension으로 변경, 하지만 Extension의 경우 String Type의
     * 모든 클래스에 적용되기 때문에 성능상의 문제가 생기진 않는가 조금 더 조사할 필요성이 있음
     */
    fun isEmail(data: String): Boolean {
        return Regex.EMAIL.toRegex().matches(data)
    }
    fun checkPassword(data: String): Boolean {
        return Regex.PASSWORD.toRegex().matches(data)
    }
}