package kr.co.treegames.tagfeed.task.account

import io.reactivex.Observable
import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.model.Key
import kr.co.treegames.tagfeed.data.source.AccountRepository
import kr.co.treegames.tagfeed.data.source.SharedPreferencesRepository

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description :
 */
class AccountPresenter(private val preferences: SharedPreferencesRepository,
                       private val repository: AccountRepository,
                       val view: AccountContract.View)
    : AccountContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
    }
    override fun signIn(account: Account) {
        view.setLoadingIndicator(true)
        repository.signIn(account, {
            preferences.put(Key.SharedPreferences.UUID, it?.id)
            view.setLoadingIndicator(false)
            view.startMainActivity()
        }, { code, message ->
            view.showMessage("code:$code:message:$message")
            view.setLoadingIndicator(false)
        })
    }
    override fun signUp(account: Account) {
        view.setLoadingIndicator(true)
        repository.signUp(account, {
            preferences.put(Key.SharedPreferences.UUID, it?.id)
            view.setLoadingIndicator(false)
        }, { code, message ->
            view.showMessage("code:$code:message:$message")
            view.setLoadingIndicator(false)
        })
    }
    override fun sample() {
    }
}