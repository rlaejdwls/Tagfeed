package kr.co.treegames.tagfeed.task.splash

import kr.co.treegames.tagfeed.task.BasePresenter
import kr.co.treegames.tagfeed.task.BaseView

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
interface SplashContract {
    interface View: BaseView<Presenter> {
        fun setLoadingIndicator(isShow: Boolean)
        fun showGooglePlayNotSupported()
        fun close()
        fun startMainActivity()
        fun startAccountActivity()
    }
    interface Presenter: BasePresenter {
        fun automatic()
    }
}