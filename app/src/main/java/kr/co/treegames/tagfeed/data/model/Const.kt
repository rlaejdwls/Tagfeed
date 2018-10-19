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