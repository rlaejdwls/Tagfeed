package kr.co.treegames.tagfeed.data.model

import android.os.Parcelable
import android.widget.TextView
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by Hwang on 2018-09-04.
 *
 * Description : 모델
 */
data class Root<out E>(val code: Int, val message: String, val data: E)
@Parcelize data class Account(var email: String, var pwd: String): Parcelable
//@Entity(indices = [Index("name"), Index("age")], foreignKeys = [ForeignKey(entity = Gender::class, parentColumns = ["id"], childColumns = ["gender"])])
@Parcelize @Entity data class User(@PrimaryKey var uuid: String, var email: String?, var name: String?): Parcelable
@Parcelize @Entity data class Session(@PrimaryKey var sessionKey: String, var uuid: String, var email: String?, var name: String?): Parcelable