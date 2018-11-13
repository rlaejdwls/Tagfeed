package kr.co.treegames.tagfeed.data.source.db

import android.content.Context
import kr.co.treegames.tagfeed.data.model.Key
import kr.co.treegames.tagfeed.data.model.Session
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.db.dao.SessionDataAccessObj
import kr.co.treegames.tagfeed.manage.AndroidRsaCipherHelper
import kr.co.treegames.tagfeed.manage.db.ORDBM
import kr.co.treegames.tagfeed.manage.runner.PoolRunner
import java.lang.Exception

/**
 * Created by Hwang on 2018-11-09.
 *
 * Description :
 */
class SessionDataSource(
        private val runner: PoolRunner,
        private val sessionDao: SessionDataAccessObj,
        private val encoder: AndroidRsaCipherHelper
) {
    companion object {
        private var instance: SessionDataSource? = null

        @JvmStatic fun getInstance(context: Context): SessionDataSource {
            return instance ?: synchronized(SessionDataSource::javaClass) {
                SessionDataSource(
                        PoolRunner(),
                        ORDBM.getInstance(context).sessionDao(),
                        AndroidRsaCipherHelper.apply { init(context) }
                ).apply { instance = this }
            }
        }
    }

    fun insert(user: User) {
        runner.local.execute { sessionDao.insert(Session(Key.USER_SESSION_DATA,
                encoder.encrypt(user.uuid), user.email, user.name)) }
    }
    fun getSession(success: (Session?) -> Unit, failure: (Int, String?) -> Unit) {
        runner.local.execute {
            var session: Session? = null
            try {
                session = sessionDao.getSession()?.apply {
                    uuid = encoder.decrypt(uuid)
                }
            } catch (e: Exception) {
                failure(e.hashCode(), e.message)
            }
            success(session)
        }
    }
    fun delete(session: Session) {
        runner.local.execute { sessionDao.delete(session) }
    }
}