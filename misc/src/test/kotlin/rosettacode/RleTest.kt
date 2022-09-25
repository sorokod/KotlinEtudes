package rosettacode

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import misc.rle

class RleTest : FunSpec({

    val testData = listOf(
        "TTESSST" to "2T1E3S1T",
        "WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWBWWWWWWWWWWWWWW" to "12W1B12W3B24W1B14W",
        "kotlin" to "1k1o1t1l1i1n"
    )

    test("rle_r test") {
        testData.forAll { (input, expected) ->
            rle(input) shouldBe expected
        }
    }

})
