package kr.co.treegames.tagfeed.data.source.db

import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.UserDataSource
import kr.co.treegames.tagfeed.data.source.db.dao.UserDataAccessObj
import kr.co.treegames.tagfeed.manage.db.ORDBM
import kr.co.treegames.tagfeed.manage.db.QueueRunner

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
object UserLocalDataSource: UserDataSource {
    private val userDao: UserDataAccessObj = ORDBM.getInstance().userDao()
    private val runner: QueueRunner = QueueRunner()

    override fun insert(user: User, success: (User) -> Unit, failure: (Int, String?) -> Unit) {
        userDao.insert(user)
    }
}