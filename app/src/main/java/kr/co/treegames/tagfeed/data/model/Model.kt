package kr.co.treegames.tagfeed.data.model

import android.os.Parcelable
import android.widget.TextView
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by Hwang on 2018-09-04.
 *
 * Description : 모델
 */
data class Root<out E>(val code: Int, val message: String, val data: E)
@Parcelize data class Account(var email: String, var pwd: String): Parcelable
@Parcelize data class User(var id: String, var email: String?, var name: String?): Parcelable