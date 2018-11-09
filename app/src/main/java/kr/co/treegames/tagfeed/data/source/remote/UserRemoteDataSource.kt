package kr.co.treegames.tagfeed.data.source.remote

import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.UserDataSource

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
object UserRemoteDataSource {
    fun insert(user: User, success: (User) -> Unit, failure: (Int, String?) -> Unit) {
        success(user)
    }
}