package kr.co.treegames.tagfeed

import kr.co.treegames.tagfeed.junit.Calculator
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @BeforeEach fun beforeEachTest() {
        System.out.println("Before Each Test")
    }
    @Test fun `2 + 2 = 4`() {
        System.out.println("Basic Test function")
        assertEquals(2 + 2, 4)
    }
    @DisplayName("left + right = result")
    @ParameterizedTest(name="{0} + {1} = {2}")
    @CsvSource("54, 46, 100", "78, 22, 100")
    fun add(left: Int, right: Int, result: Int) {
        System.out.println("Parameterized test")
        val calculator = Calculator()
        assertEquals(result, calculator.add(left, right)) {
            "$left + $right should equal $result"
        }
    }
    @RepeatedTest(value=2, name="Custom name {currentRepetition}/{totalRepetitions}")
    fun repeatedTest(testInfo: RepetitionInfo) {
        System.out.println("Executing repeated test #${testInfo.currentRepetition}")
        assertEquals(2, Math.addExact(1, 1), "1 + 1 should equal 2")
    }
    @AfterEach fun afterEachTest() {
        System.out.println("After Each Test")
        System.out.println("=====================")
    }
}
