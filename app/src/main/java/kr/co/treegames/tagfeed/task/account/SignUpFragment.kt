package kr.co.treegames.tagfeed.task.account

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kr.co.treegames.tagfeed.task.DefaultFragment

/**
 * Created by Hwang on 2018-10-18.
 *
 * Description :
 */
class SignUpFragment: DefaultFragment(), AccountContract.View.SignUpView {
    override fun getEmail(): String {
        return edt_email.text.toString()
    }
    override fun getPassword(): String {
        return edt_password.text.toString()
    }
    override fun verification(): Pair<Boolean, String?> {
        return Pair(true, null)
    }
}