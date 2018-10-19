package kr.co.treegames.core.util

/**
 * Created by Hwang on 2018-10-18.
 *
 * Description :
 */
class StringUtils {
    companion object {
        fun toAlias(data: String): String {
            var result = ""
            var isConvert = false

            for (ch in data.toCharArray()) {
                if (isConvert) {
                    isConvert = false
                    if (ch in 'a'..'z') {
                        result += (ch.toInt() - ('a' - 'A')).toChar()
                    }
                } else {
                    when (ch) {
                        '_' -> isConvert = true
                        in 'A'..'Z' -> result += "_" + (ch.toInt() + ('a' - 'A')).toChar()
                        else -> result += ch
                    }
                }
            }
            return result
        }
        fun getAliasWithUnderBar(data: String): String {
            var result = ""
            for (i in 0 until data.length) {
                val ch = data[i]
                result += if (ch.toInt() in 65..90 && i == 0) {
                    Character.toString((ch.toInt() + 32).toChar())
                } else if (ch.toInt() in 65..90) {
                    "_" + Character.toString((ch.toInt() + 32).toChar())
                } else {
                    Character.toString(ch)
                }
            }
            return result
        }
    }
}