package kr.co.treegames.tagfeed.data.model

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description : 상수값 정의
 */
interface Key {
    interface Intent {
        companion object {
            const val DATA: String = "DATA"
        }
    }
    interface SharedPreferences {
        companion object {
            const val DEFAULT_NAME: String = "default_name"
            const val UUID: String = "UUID"
        }
    }
}
interface Regex {
    companion object {
        const val EMAIL: String = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"
//        "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{8,}\$"
        const val PASSWORD: String = "^(?=.*?[a-zA-Z0-9]).{6,}\$"
    }
}