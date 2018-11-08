package kr.co.treegames.tagfeed.data.source

import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.db.AccountLocalDataSource
import kr.co.treegames.tagfeed.data.source.remote.AccountRemoteDataSource

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description :
 */
object AccountRepository: AccountDataSource {
    private val remote: AccountDataSource = AccountRemoteDataSource
    private val local: AccountDataSource = AccountLocalDataSource
//class AccountRepository(private val remote: AccountDataSource, private val local: AccountDataSource): AccountDataSource {
//    companion object {
//        private var INSTANCE: AccountRepository? = null
//
//        @JvmStatic fun getInstance(remote: AccountDataSource, local: AccountDataSource): AccountRepository {
//            return INSTANCE ?: synchronized(AccountRepository::javaClass) {
//                AccountRepository(remote, local).apply { INSTANCE = this }
//            }
//        }
//        @VisibleForTesting fun clearInstance() {
//            INSTANCE = null
//        }
//    }

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