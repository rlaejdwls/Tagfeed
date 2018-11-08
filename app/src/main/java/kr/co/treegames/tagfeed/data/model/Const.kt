package kr.co.treegames.tagfeed.data.model

import kr.co.treegames.tagfeed.App
import kr.co.treegames.tagfeed.R

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description : 상수값 정의
 */
object Const {
    val DOMAIN: String = App.get().resources.getString(R.string.app_domain_api)
}
interface Key {
    object SharedPreferences {
        const val DEFAULT_NAME: String = "default_name"
        const val UUID: String = "UUID"
    }
}
object Regex {
    const val EMAIL: String = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
    const val PASSWORD: String = "^(?=.*?[a-zA-Z0-9]).{6,}\$" //"^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
}