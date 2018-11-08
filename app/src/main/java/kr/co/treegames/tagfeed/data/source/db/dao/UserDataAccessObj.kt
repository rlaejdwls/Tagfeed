package kr.co.treegames.tagfeed.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import kr.co.treegames.tagfeed.data.model.User

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description :
 */
@Dao
interface UserDataAccessObj {
    @Query("SELECT * FROM user")
    fun getUsers(): Flowable<User>

    @Query("SELECT * FROM user WHERE uuid = :uuid")
    fun getUser(uuid: String): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}