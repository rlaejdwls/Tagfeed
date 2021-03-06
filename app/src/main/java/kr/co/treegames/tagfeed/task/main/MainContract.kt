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
        fun startAccountActivity()
    }
    interface Presenter: BasePresenter {
        fun signOut()
        /**
         * 메일 전송 테스트
         */
        fun sendEmail(title: String, contents: String, receiver: String, sender: String, passwordOfSender: String/*, fileName: String?, filePath: String?*/)
    }
}