package kr.co.treegames.tagfeed.task.splash

import kr.co.treegames.core.manage.Logger
import kr.co.treegames.tagfeed.data.model.Key
import kr.co.treegames.tagfeed.data.source.AccountRepository
import kr.co.treegames.tagfeed.data.source.SharedPreferencesDataSource

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
class SplashPresenter(private val preferences: SharedPreferencesDataSource,
                      private val repository: AccountRepository,
                      val view: SplashContract.View)
    : SplashContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
    }
    override fun automatic() {
        view.setLoadingIndicator(true)
        repository.automatic({
            preferences.put(Key.SharedPreferences.UUID, it?.id)
            view.setLoadingIndicator(false)
            view.startMainActivity()
        }, { code, message ->
            view.showMessage("code:$code:message:$message")
            view.setLoadingIndicator(false)
            view.startAccountActivity()
        })
    }
}