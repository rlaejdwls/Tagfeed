package kr.co.treegames.tagfeed.data.source.db.dao

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.manage.db.ORDBM
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Hwang on 2018-11-08.
 *
 * Description : DAO 유닛 테스트를 해보려고 함
 */
@RunWith(AndroidJUnit4::class)
class UserDataAccessObjTest {
    private val user: User = User("1234567890123456789012345678", "local.test@gmail.com", null)
    private lateinit var userDao: UserDataAccessObj

    @Before fun setup() {
        userDao = ORDBM.getInstance(InstrumentationRegistry.getTargetContext()).userDao()
    }

    @Throws(Exception::class)
    @Test fun insertUser() {
        val result = userDao.insert(user)
        assertTrue(result > 0)

        val selectUser = userDao.getUser(user.uuid)
        assertNotNull(selectUser)
        assertEquals(selectUser.uuid, user.uuid)
        userDao.delete(user)
    }
}