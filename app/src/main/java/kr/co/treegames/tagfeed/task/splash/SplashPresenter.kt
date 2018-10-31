package kr.co.treegames.tagfeed.task.splash

import kr.co.treegames.core.manage.Logger
import kr.co.treegames.tagfeed.data.source.AccountRepository
import kr.co.treegames.tagfeed.data.source.SharedPreferencesRepository
import kr.co.treegames.tagfeed.manage.GoogleApiManager

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
class SplashPresenter(private val preferences: SharedPreferencesRepository,
                      private val repository: AccountRepository,
                      private val api: GoogleApiManager,
                      val view: SplashContract.View)
    : SplashContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
    }
    override fun automatic() {
        api.isGooglePlayServicesAvailable({
            view.setLoadingIndicator(true)
            repository.automatic({
//                preferences.put(Key.SharedPreferences.UUID, it?.id)
                view.setLoadingIndicator(false)
                view.startMainActivity()
            }, { code, message ->
                Logger.d("code:$code:message:$message")
                view.setLoadingIndicator(false)
                view.startAccountActivity()
            })
        }, {
            view.close()
        }, view::showGooglePlayNotSupported)
    }
}