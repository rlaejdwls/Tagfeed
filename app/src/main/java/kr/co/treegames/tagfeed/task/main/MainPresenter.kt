package kr.co.treegames.tagfeed.task.main

import kr.co.treegames.tagfeed.data.model.Key
import kr.co.treegames.tagfeed.data.source.SharedPreferencesRepository

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
class MainPresenter(private val preferences: SharedPreferencesRepository,
                    val view: MainContract.View)
    : MainContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        view.showMessage("uuid:" + preferences.getString(Key.SharedPreferences.UUID))
    }
}