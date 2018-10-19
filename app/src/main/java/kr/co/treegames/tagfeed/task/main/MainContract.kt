package kr.co.treegames.tagfeed.task.main

import kr.co.treegames.tagfeed.task.BasePresenter
import kr.co.treegames.tagfeed.task.BaseView

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
interface MainContract {
    interface View: BaseView<Presenter> {
        fun showMessage(message: String)
    }
    interface Presenter: BasePresenter {
    }
}