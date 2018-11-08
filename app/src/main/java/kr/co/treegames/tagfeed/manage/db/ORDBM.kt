package kr.co.treegames.tagfeed.manage.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.co.treegames.tagfeed.App
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.db.dao.UserDataAccessObj

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
@Database(entities=[User::class], version=1)
abstract class ORDBM: RoomDatabase() {
    companion object {
        private var instance: ORDBM? = null

        @JvmStatic fun getInstance(): ORDBM {
            return instance ?: synchronized(ORDBM::class) {
                Room.databaseBuilder(App.get().applicationContext, ORDBM::class.java, "tagfeed.db")
                        .build().apply { instance = this }
            }
        }
    }

    abstract fun userDao(): UserDataAccessObj
}