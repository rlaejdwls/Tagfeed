package kr.co.treegames.tagfeed.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import kr.co.treegames.tagfeed.data.model.Account
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.AccountDataSource

/**
 * Created by Hwang on 2018-09-03.
 *
 * Description : 로그인, 회원가입에 관련 리모트 데이터 소스
 */
object AccountRemoteDataSource: AccountDataSource {
    private val auth = FirebaseAuth.getInstance()
//class AccountRemoteDataSource(private val auth: FirebaseAuth): AccountDataSource {
//    companion object {
//        private var INSTANCE: AccountRemoteDataSource? = null
//
//        @JvmStatic fun getInstance(): AccountRemoteDataSource {
//            return INSTANCE ?: synchronized(AccountRemoteDataSource::javaClass) {
//                AccountRemoteDataSource(FirebaseAuth.getInstance()).apply { INSTANCE = this }
//            }
//        }
//        @VisibleForTesting fun clearInstance() {
//            INSTANCE = null
//        }
//    }

    override fun automatic(success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        auth.currentUser?.let { User(it.uid, it.email, it.displayName) }?.also(success) ?: failure(-1, "You are not signed in")
    }
//    ("ERROR_INVALID_CUSTOM_TOKEN", "The custom token format is incorrect. Please check the documentation."));
//    ("ERROR_CUSTOM_TOKEN_MISMATCH", "The custom token corresponds to a different audience."));
//    ("ERROR_INVALID_CREDENTIAL", "The supplied auth credential is malformed or has expired."));
//    ("ERROR_INVALID_EMAIL", "The email address is badly formatted."));
//    ("ERROR_WRONG_PASSWORD", "The password is invalid or the user does not have a password."));
//    ("ERROR_USER_MISMATCH", "The supplied credentials do not correspond to the previously signed in user."));
//    ("ERROR_REQUIRES_RECENT_LOGIN", "This operation is sensitive and requires recent authentication. Log in again before retrying this request."));
//    ("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."));
//    ("ERROR_EMAIL_ALREADY_IN_USE", "The email address is already in use by another account."));
//    ("ERROR_CREDENTIAL_ALREADY_IN_USE", "This credential is already associated with a different user account."));
//    ("ERROR_USER_DISABLED", "The user account has been disabled by an administrator."));
//    ("ERROR_USER_TOKEN_EXPIRED", "The user\'s credential is no longer valid. The user must sign in again."));
//    ("ERROR_USER_NOT_FOUND", "There is no user record corresponding to this identifier. The user may have been deleted."));
//    ("ERROR_INVALID_USER_TOKEN", "The user\'s credential is no longer valid. The user must sign in again."));
//    ("ERROR_OPERATION_NOT_ALLOWED", "This operation is not allowed. You must enable this service in the console."));
//    ("ERROR_WEAK_PASSWORD", "The given password is invalid."));
    /**
     * Exception 참고 자료 : https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuthException
     */
    override fun signInWithEmailAndPassword(account: Account?, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        account?.run { auth.signInWithEmailAndPassword(email, pwd)
                .addOnSuccessListener { automatic(success, failure) }
                .addOnFailureListener { e -> when (e) {
//                    is FirebaseAuthWeakPasswordException -> Logger.d("code:${e.errorCode}:message:${e.message}")
//                    is FirebaseAuthInvalidCredentialsException -> Logger.d("code:${e.errorCode}:message:${e.message}")
//                    is FirebaseAuthUserCollisionException -> Logger.d("code:${e.errorCode}:message:${e.message}")
                    is FirebaseAuthException -> failure(e.errorCode.hashCode(), e.message)
//                    is FirebaseException -> Logger.d("message:${e.message}")
                    else -> failure(-3, e.message)
                }}}
    }
    override fun signInWithCredential(token: String, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential)
                .addOnSuccessListener { automatic(success, failure) }
                .addOnFailureListener { e -> when (e) {
                    is FirebaseAuthException -> failure(e.errorCode.hashCode(), e.message)
                    else -> failure(-4, e.message)
                }}
    }
    override fun signUp(account: Account?, success: (User?) -> Unit, failure: (Int, String?) -> Unit) {
        account?.run { auth.createUserWithEmailAndPassword(email, pwd)
                .addOnSuccessListener { automatic(success, failure) }
                .addOnFailureListener { e -> when (e) {
//                    is FirebaseAuthWeakPasswordException -> Logger.d("code:${e.errorCode}:message:${e.message}")
//                    is FirebaseAuthInvalidCredentialsException -> Logger.d("code:${e.errorCode}:message:${e.message}")
//                    is FirebaseAuthUserCollisionException -> Logger.d("code:${e.errorCode}:message:${e.message}")
                    is FirebaseAuthException -> failure(e.errorCode.hashCode(), e.message)
                    else -> { failure(-2, e.message) }
                }}}
    }
    override fun signOut() {
        auth.signOut()
    }
}