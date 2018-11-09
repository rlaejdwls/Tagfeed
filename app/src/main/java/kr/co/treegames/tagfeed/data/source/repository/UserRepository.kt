package kr.co.treegames.tagfeed.data.source.repository

import android.content.Context
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.UserDataSource
import kr.co.treegames.tagfeed.data.source.db.SessionDataSource
import kr.co.treegames.tagfeed.data.source.remote.UserRemoteDataSource
import kr.co.treegames.tagfeed.manage.AndroidRsaCipherHelper

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
class UserRepository(
        private val remote: UserRemoteDataSource,
        private val session: SessionDataSource
): UserDataSource {
    companion object {
        private var instance: UserRepository? = null

        @JvmStatic fun getInstance(context: Context): UserRepository {
            return instance ?: synchronized(UserRepository::javaClass) {
                UserRepository(UserRemoteDataSource, SessionDataSource.getInstance(context)).apply { instance = this }
            }
        }
    }

    override fun insert(user: User, success: (User) -> Unit, failure: (Int, String?) -> Unit) {
        remote.insert(user, {
            session.insert(user)
            success(it)
        }, failure)
    }
}