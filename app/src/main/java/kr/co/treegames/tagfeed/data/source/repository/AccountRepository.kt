package kr.co.treegames.tagfeed.data.source.repository

import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.AccountDataSource
import kr.co.treegames.tagfeed.data.source.remote.AccountRemoteDataSource

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description :
 */
object AccountRepository: AccountDataSource {
    private val remote = AccountRemoteDataSource

    override fun automatic(success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        remote.automatic(success, failure)
    }
    override fun signInWithEmailAndPassword(account: Account?, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        remote.signInWithEmailAndPassword(account, success, failure)
    }
    override fun signInWithCredential(token: String, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        remote.signInWithCredential(token, success, failure)
    }
    override fun signUp(account: Account?, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        remote.signUp(account, success, failure)
    }
    override fun signOut() {
        remote.signOut()
    }
}