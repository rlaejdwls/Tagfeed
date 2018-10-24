package kr.co.treegames.tagfeed.task.account

import kotlinx.android.synthetic.main.fragment_sign_up.*
import kr.co.treegames.tagfeed.task.DefaultFragment

/**
 * Created by Hwang on 2018-10-18.
 *
 * Description : 회원가입을 위한 Account 서브 뷰
 */
class SignUpFragment: DefaultFragment(), AccountContract.View.SignUpView {
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