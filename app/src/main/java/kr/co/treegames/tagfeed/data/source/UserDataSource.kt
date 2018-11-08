package kr.co.treegames.tagfeed.data.source

import kr.co.treegames.tagfeed.data.model.User

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
interface UserDataSource {
    fun insert(user: User, success: (User) -> Unit, failure: (Int, String?) -> Unit)
}