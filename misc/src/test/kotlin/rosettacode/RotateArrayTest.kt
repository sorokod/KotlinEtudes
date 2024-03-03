package rosettacode

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RotateArrayTest : StringSpec({

    val array1to5 = arrayOf(1, 2, 3, 4, 5)

    "rotate an array by 2" {
        array1to5.rotate(2) shouldBe arrayOf(3, 4, 5, 1, 2)
    }

    "rotate an array by -2" {
        array1to5.rotate(-2) shouldBe arrayOf(4, 5, 1, 2, 3)
    }

    "rotate an array by 0" {
        array1to5.rotate(0) shouldBe array1to5
    }

    "rotate an empty array" {
        emptyArray<Any>().rotate(2) shouldBe emptyArray()
    }
})
