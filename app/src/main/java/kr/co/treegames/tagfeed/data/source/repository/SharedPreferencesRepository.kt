package kr.co.treegames.tagfeed.data.source.repository

import androidx.annotation.VisibleForTesting
import kr.co.treegames.tagfeed.data.source.SharedPreferencesDataSource
import kr.co.treegames.tagfeed.data.source.io.SharedPreferencesLocalDataSource

/**
 * Created by Hwang on 2018-09-05.
 *
 * Description :
 */
class SharedPreferencesRepository(private val local:SharedPreferencesLocalDataSource): SharedPreferencesDataSource {
    companion object {
        private var instance: SharedPreferencesRepository? = null

        @JvmStatic fun getInstance(local: SharedPreferencesLocalDataSource): SharedPreferencesRepository {
            return instance
                    ?: synchronized(SharedPreferencesRepository::javaClass) {
                SharedPreferencesRepository(local).apply { instance = this }
            }
        }
        @VisibleForTesting fun clearInstance() {
            instance = null
        }
    }
    override fun put(key: String, strValue: String?, intValue: Int?, boolValue: Boolean?) {
        local.put(key, strValue, intValue, boolValue)
    }
    override fun getString(key: String): String? {
        return local.getString(key)
    }
}