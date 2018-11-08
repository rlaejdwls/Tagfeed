package kr.co.treegames.tagfeed

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kr.co.treegames.tagfeed.data.source.AccountRepository
import kr.co.treegames.tagfeed.data.source.SharedPreferencesRepository
import kr.co.treegames.tagfeed.data.source.UserRepository
import kr.co.treegames.tagfeed.data.source.db.AccountLocalDataSource
import kr.co.treegames.tagfeed.data.source.io.SharedPreferencesLocalDataSource
import kr.co.treegames.tagfeed.data.source.remote.AccountRemoteDataSource
import kr.co.treegames.tagfeed.manage.GoogleApiManager
import kr.co.treegames.tagfeed.manage.ResourceManager
import kr.co.treegames.tagfeed.manage.Utilities
import kr.co.treegames.tagfeed.task.DefaultFragment

/**
 * Created by Hwang on 2018-09-04.
 *
 * Description :
 */
object Injection {
    fun provideAccountRepository(): AccountRepository {
        return AccountRepository
    }
    fun provideUserRepository(): UserRepository {
        return UserRepository
    }
    fun provideSharedPreferences(context: Context): SharedPreferencesRepository {
        return SharedPreferencesRepository.getInstance(SharedPreferencesLocalDataSource.getInstance(context))
    }
    fun provideResourceManager(context: Context): ResourceManager {
        return ResourceManager(context)
    }
    fun provideGoogleApiManager(activity: Activity): GoogleApiManager {
        return GoogleApiManager(activity)
    }
    fun provideUtilities(context: Context): Utilities {
        return Utilities(context)
    }
    fun <T: DefaultFragment> provideFragment(clazz: Class<T>): T {
        return clazz.newInstance()
    }
    fun <T: DefaultFragment> provideFragment(clazz: Class<T>, params: Bundle?): T? {
        val fragment: T? = clazz.newInstance()
        params?.let {
            fragment?.arguments = params
        }
        return fragment
    }
}