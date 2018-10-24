package kr.co.treegames.tagfeed.task.account

import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.task.BasePresenter
import kr.co.treegames.tagfeed.task.BaseView

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description : Account Contract
 */
interface AccountContract {
    interface View: BaseView<Presenter> {
        interface SignInView {
            fun getEmail(): String
            fun getPassword(): String
            fun showEmailError(error: String)
            fun showPasswordError(error: String)
        }
        interface SignUpView {
            fun getEmail(): String
            fun getPassword(): String
            fun showEmailError(error: String)
            fun showPasswordError(error: String)
        }
        fun setLoadingIndicator(isShow: Boolean)
        fun showMessage(message: String)
        fun showEmailEmptyError()
        fun showPasswordEmptyError()
        fun showCheckingEmailFormat()
        fun showCheckingPasswordFormat()
        fun startMainActivity()
    }
    interface Presenter: BasePresenter {
        fun signIn(account: Account)
        fun signUp(account: Account)
        fun validation(email: String, password: String): Boolean
    }
}