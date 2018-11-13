package kr.co.treegames.tagfeed.manage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kr.co.treegames.tagfeed.App
import kr.co.treegames.tagfeed.data.model.Session
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.db.dao.SessionDataAccessObj
import kr.co.treegames.tagfeed.data.source.db.dao.UserDataAccessObj

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
@Database(entities=[User::class, Session::class], version=2)
abstract class ORDBM: RoomDatabase() {
    companion object {
        private var instance: ORDBM? = null

        private val migration_1_2 = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("")
            }
        }

        @JvmStatic fun getInstance(context: Context): ORDBM {
            return instance ?: synchronized(ORDBM::class) {
                Room.databaseBuilder(context.applicationContext ?: context, ORDBM::class.java, "tagfeed.db")
//                        .addMigrations(migration_1_2)
                        .fallbackToDestructiveMigration()
                        .build().apply { instance = this }
            }
        }
    }

    abstract fun userDao(): UserDataAccessObj
    abstract fun sessionDao(): SessionDataAccessObj
}