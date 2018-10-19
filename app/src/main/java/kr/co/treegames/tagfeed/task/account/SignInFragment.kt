package kr.co.treegames.tagfeed.task.account

import kotlinx.android.synthetic.main.fragment_sign_in.*
import kr.co.treegames.tagfeed.data.model.REGEX
import kr.co.treegames.tagfeed.task.DefaultFragment

/**
 * Created by Hwang on 2018-10-18.
 *
 * Description :
 */
class SignInFragment: DefaultFragment(), AccountContract.View.SignInView {
    override fun getEmail(): String {
        return edt_email.text.toString()
    }
    override fun getPassword(): String {
        return edt_password.text.toString()
    }
    override fun verification(): Pair<Boolean, String?> {
        if (!REGEX.EMAIL.toRegex().matches(edt_email.text)) {
            return Pair(false, "이메일 형식을 확인해주세요.")
        }
        return Pair(true, null)
    }
}