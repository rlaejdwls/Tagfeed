package kr.co.treegames.tagfeed.manage

import android.app.Activity
import android.content.DialogInterface
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

/**
 * Created by Hwang on 2018-10-24.
 *
 * Description : 임시 API 매니저
 * - 현재로서는 통신도 없고 일회성이라 매니저로 빠짐
 */
class GoogleApiManager(private val activity: Activity) {
    companion object {
        const val REQUEST_CODE: Int = 100
    }
    fun isGooglePlayServicesAvailable(success: () -> Unit, failure: (DialogInterface) -> Unit, reject: () -> Unit) {
        val googleApi = GoogleApiAvailability.getInstance()
        val code = googleApi.isGooglePlayServicesAvailable(activity)
        when (code) {
            ConnectionResult.SUCCESS -> success()
            else -> {
                if (googleApi.isUserResolvableError(code)) {
                    googleApi.getErrorDialog(activity, code, REQUEST_CODE, failure).show()
                } else { reject() }
            }
        }
    }
}