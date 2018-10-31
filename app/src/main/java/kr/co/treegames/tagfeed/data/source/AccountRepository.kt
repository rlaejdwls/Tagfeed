package kr.co.treegames.tagfeed.data.source

import androidx.annotation.VisibleForTesting
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.model.User

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description :
 */
class AccountRepository(private val remote: AccountDataSource, private val local: AccountDataSource): AccountDataSource {
    companion object {
        private var INSTANCE: AccountRepository? = null

        @JvmStatic fun getInstance(remote: AccountDataSource, local: AccountDataSource): AccountRepository {
            return INSTANCE ?: synchronized(AccountRepository::javaClass) {
                AccountRepository(remote, local).apply { INSTANCE = this }
            }
        }
        @VisibleForTesting fun clearInstance() {
            INSTANCE = null
        }
    }

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