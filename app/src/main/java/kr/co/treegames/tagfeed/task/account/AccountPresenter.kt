package kr.co.treegames.tagfeed.task.account

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kr.co.treegames.core.manage.Logger
import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.source.AccountRepository
import kr.co.treegames.tagfeed.data.source.SharedPreferencesRepository
import kr.co.treegames.tagfeed.extension.checkPassword
import kr.co.treegames.tagfeed.extension.isEmail
import kr.co.treegames.tagfeed.manage.ResourceManager

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description : 로그인, 회원가입 관련 프레젠터
 */
class AccountPresenter(private val preferences: SharedPreferencesRepository,
                       private val repository: AccountRepository,
                       private val resources: ResourceManager,
                       val view: AccountContract.View)
    : AccountContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
    }
    override fun signInWithEmailAndPassword(account: Account) {
        if (!validation(account.email, account.pwd)) {
            return
        }
        view.setLoadingIndicator(true)
        repository.signInWithEmailAndPassword(account, {
//            preferences.put(Key.SharedPreferences.UUID, it?.id)
            view.setLoadingIndicator(false)
            view.startMainActivity()
        }, { code, message ->
            Logger.d("code:$code:message:$message")
            view.setLoadingIndicator(false)
        })
    }
    override fun signInWithCredential(token: String) {
        view.setLoadingIndicator(true)
        repository.signInWithCredential(token, {
            view.setLoadingIndicator(false)
            view.startMainActivity()
        }, { code, message ->
            Logger.d("code:$code:message:$message")
            view.setLoadingIndicator(false)
        })
    }
    override fun signUp(account: Account) {
        if (!validation(account.email, account.pwd)) {
            return
        }
        view.setLoadingIndicator(true)
        repository.signUp(account, {
//            preferences.put(Key.SharedPreferences.UUID, it?.id)
            view.setLoadingIndicator(false)
        }, { code, message ->
            Logger.d("code:$code:message:$message")
            view.setLoadingIndicator(false)
        })
    }

    override fun validation(email: String, password: String): Boolean {
        var isResult = true
        if (email.isEmpty().also { isResult = !it }) {
            view.showEmailEmptyError()
//            view.showEmailError(resources.getString("error_this_field_is_required"))
        }
        if (password.isEmpty().also { isResult = !it }) {
            view.showPasswordEmptyError()
        }
        if (!email.isEmpty() && !email.isEmail().also { isResult = it }) {
            view.showCheckingEmailFormat()
        }
        if (!password.isEmpty() && !password.checkPassword().also { isResult = it }) {
            view.showCheckingPasswordFormat()
        }
        return isResult
    }
}