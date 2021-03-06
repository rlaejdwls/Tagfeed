package kr.co.treegames.tagfeed.task.account

import kotlinx.android.synthetic.main.fragment_sign_in.*
import kr.co.treegames.tagfeed.task.DefaultFragment

/**
 * Created by Hwang on 2018-10-18.
 *
 * Description : 로그인을 위한 Account 서브 뷰
 */
class SignInFragment: DefaultFragment(), AccountContract.View.SignInView {
    override fun getEmail(): String {
        return edt_email.text.toString()
    }
    override fun getPassword(): String {
        return edt_password.text.toString()
    }
    override fun showEmailError(error: String) {
        edt_email.error = error
    }
    override fun showPasswordError(error: String) {
        edt_password.error = error
    }
}