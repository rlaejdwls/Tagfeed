package kr.co.treegames.tagfeed

import kr.co.treegames.tagfeed.extension.checkPassword
import kr.co.treegames.tagfeed.extension.isEmail
import org.junit.Assert
import org.junit.Test

/**
 * Created by Hwang on 2018-10-23.
 *
 * Description : 유효성 검사용 유닛 테스트 클래스
 */
class ValidationUnitTest {
    @Test fun emailValidation_CorrectEmailSimple_ReturnsTrue() {
        Assert.assertTrue("email@address.com".isEmail())
    }
    @Test fun emailValidation_CorrectEmailSubDomain_ReturnsTrue() {
        Assert.assertTrue("name@email.co.uk".isEmail())
    }
    @Test fun emailValidation_InvalidEmailNoTld_ReturnsFalse() {
        Assert.assertFalse("name@email".isEmail())
    }
    @Test fun passwordValidation_CorrectPassword_ReturnsTrue() {
        Assert.assertTrue("abcdefghijk".checkPassword())
    }
    @Test fun passwordValidation_InvalidPassword_ReturnsTrue() {
        Assert.assertFalse("123456".checkPassword())
    }
}