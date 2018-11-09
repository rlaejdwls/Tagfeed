package kr.co.treegames.tagfeed.data.source.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kr.co.treegames.tagfeed.data.model.Key
import kr.co.treegames.tagfeed.data.model.Session

/**
 * Created by Hwang on 2018-11-09.
 *
 * Description :
 */
@Dao
interface SessionDataAccessObj {
    @Query("SELECT * FROM session WHERE sessionKey = \"" + Key.USER_SESSION_DATA + "\"")
    fun getSession(): LiveData<Session>

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insert(session: Session): Long
    @Update
    fun update(session: Session)
    @Delete
    fun delete(session: Session)
}