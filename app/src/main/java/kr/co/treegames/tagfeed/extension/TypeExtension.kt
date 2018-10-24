package kr.co.treegames.tagfeed.extension

import kr.co.treegames.tagfeed.data.model.Regex

/**
 * Created by Hwang on 2018-10-23.
 *
 * Description : 타입 관련 확장 함수들
 * - Extension이 편리하고 좋으나 자바코드로 어떻게 해석되는가 알아둘 필요성이 있음
 */
fun String.isEmail(): Boolean = Regex.EMAIL.toRegex().matches(this)
fun String.checkPassword(): Boolean = Regex.PASSWORD.toRegex().matches(this)
