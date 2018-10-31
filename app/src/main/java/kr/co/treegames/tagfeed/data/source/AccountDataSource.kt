package kr.co.treegames.tagfeed.data.source

import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.model.User

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description :
 */
interface AccountDataSource {
    fun automatic(success: (User?) -> Unit, failure: (Int, String?) -> Unit)
    fun signInWithEmailAndPassword(account: Account? = null, success: (User?) -> Unit, failure: (Int, String?) -> Unit)
    fun signInWithCredential(token: String, success: (User?) -> Unit, failure: (Int, String?) -> Unit)
    fun signUp(account: Account? = null, success: (User?) -> Unit, failure: (Int, String?) -> Unit)
    fun signOut()
}