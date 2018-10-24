package kr.co.treegames.tagfeed.data.source.db

import androidx.annotation.VisibleForTesting
import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.AccountDataSource

/**
 * Created by Hwang on 2018-10-24.
 *
 * Description :
 */
class AccountLocalDataSource: AccountDataSource {
    companion object {
        private var INSTANCE: AccountLocalDataSource? = null

        @JvmStatic fun getInstance(): AccountLocalDataSource {
            return INSTANCE ?: synchronized(AccountLocalDataSource::javaClass) {
                AccountLocalDataSource().apply { INSTANCE = this }
            }
        }
        @VisibleForTesting fun clearInstance() {
            INSTANCE = null
        }
    }

    override fun automatic(success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
    }
    override fun signIn(account: Account?, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
    }
    override fun signUp(account: Account?, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
    }
    override fun signOut() {
    }
}