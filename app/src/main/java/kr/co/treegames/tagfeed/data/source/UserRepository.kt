package kr.co.treegames.tagfeed.data.source

import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.db.UserLocalDataSource
import kr.co.treegames.tagfeed.data.source.remote.UserRemoteDataSource

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
object UserRepository: UserDataSource {
    private val remote: UserDataSource = UserRemoteDataSource
    private val local: UserDataSource = UserLocalDataSource

    override fun insert(user: User, success: (User) -> Unit, failure: (Int, String?) -> Unit) {
        remote.insert(user, { local.insert(it, success, failure) }, failure)
    }
}