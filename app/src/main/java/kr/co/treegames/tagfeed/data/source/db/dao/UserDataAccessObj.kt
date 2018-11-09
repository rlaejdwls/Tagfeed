package kr.co.treegames.tagfeed.data.source.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable
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
    fun getUser(uuid: String): User

    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insert(user: User): Long
    @Insert(onConflict=OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Update
    fun update(user: User)
    @Update
    fun updateAll(vararg user: User)

    @Delete
    fun delete(user: User)
    @Delete
    fun deleteAll(vararg user: User)
}