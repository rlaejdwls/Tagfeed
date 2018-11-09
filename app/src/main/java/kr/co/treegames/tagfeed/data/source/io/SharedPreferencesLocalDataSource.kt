package kr.co.treegames.tagfeed.data.source.io

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import kr.co.treegames.tagfeed.data.model.Const
import kr.co.treegames.tagfeed.data.source.SharedPreferencesDataSource

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
class SharedPreferencesLocalDataSource(private val preferences: SharedPreferences): SharedPreferencesDataSource {
    companion object {
        private var instance: SharedPreferencesLocalDataSource? = null

        @JvmStatic fun getInstance(context: Context): SharedPreferencesLocalDataSource {
            return instance ?: synchronized(SharedPreferencesLocalDataSource::javaClass) {
                SharedPreferencesLocalDataSource(context.getSharedPreferences(Const.DEFAULT_NAME,
                        Activity.MODE_PRIVATE)).apply { instance = this }
            }
        }
        @VisibleForTesting fun clearInstance() {
            instance = null
        }
    }
    override fun put(key: String, strValue: String?, intValue: Int?, boolValue: Boolean?) {
        with(preferences.edit()) {
            strValue?.let { putString(key, strValue) }
            intValue?.let { putInt(key, intValue) }
            boolValue?.let { putBoolean(key, boolValue) }
            apply()
        }
    }
    override fun getString(key: String): String? {
        return preferences.getString(key, null)
    }
}