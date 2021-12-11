package misc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class RleTest {

    companion object {
        @JvmStatic
        fun provideTestArguments() =
            arrayOf(
                Arguments.of("TTESSST", "2T1E3S1T"),
                Arguments.of(
                    "WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWBWWWWWWWWWWWWWW",
                    "12W1B12W3B24W1B14W"
                ),
                Arguments.of("kotlin", "1k1o1t1l1i1n")
            )
    }

    @ParameterizedTest
    @MethodSource("provideTestArguments")
    fun `rle_r test`(text: String, expected: String) {
        assertEquals(expected, rle(text))
    }

    @ParameterizedTest
    @MethodSource("provideTestArguments")
    fun `rle test`(text: String, expected: String) {
        assertEquals(expected, rle_r(text))
    }

}
