package kr.co.treegames.tagfeed.data.source.db.dao

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import kr.co.treegames.tagfeed.data.model.User
import kr.co.treegames.tagfeed.data.source.db.SessionDataSource
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify

/**
 * Created by Hwang on 2018-11-12.
 *
 * Description :
 */
@RunWith(AndroidJUnit4::class)
class SessionDataAccessObjTest {
    private val user: User = User("1234567890123456789012345678", "local.test@gmail.com", null)
    private lateinit var sessionDataSource: SessionDataSource

    @Before fun setup() {
        sessionDataSource = SessionDataSource.getInstance(InstrumentationRegistry.getContext())
    }
    @Test fun insertSession() {
        sessionDataSource.insert(user)

//        sessionDataSource.getSession({ session ->
//            assertEquals(verify(session).uuid, user.uuid)
//            assertNotNull(session)
//            assertEquals(session?.uuid, user.uuid)
//            sessionDataSource.delete(session)
//        }, { code, message ->
//            assertTrue(false)
//        })
    }
}