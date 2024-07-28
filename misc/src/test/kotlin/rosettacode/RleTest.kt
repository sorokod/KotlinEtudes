package rosettacode

import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class RleTest : StringSpec({

    val testData = listOf(
        "" to "",
        "TTESSST" to "2T1E3S1T",
        "rosetta" to "1r1o1s1e2t1a",
        "kotlin" to "1k1o1t1l1i1n",
        "3333" to "43"
    )

    "rle test" {
        testData.forAll { (input, expected) ->
            rle(input) shouldBe expected
        }
    }

})
