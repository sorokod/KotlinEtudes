package rosettacode

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class GcdTest : FunSpec({

    context("gcd recursive") {
        withData<Triple<Long, Long, Long>>(
            Triple(0, 0, 0),
            Triple(11, 7, 1),
            Triple(10, 35, 5),
            Triple(10, 31, 1),
            Triple(80, 62, 2),
            Triple(120, 18, 6),
        ) { (a, b, expected) ->
            gcdR(a, b) shouldBe expected
        }
    }

    context("lcm") {
        withData<Triple<Long, Long, Long>>(
            Triple(11, 7, 77),
            Triple(10, 35, 70),
            Triple(10, 31, 310),
            Triple(80, 62, 2480),
            Triple(120, 18, 360),
        ) { (a, b, expected) ->
            lcm(a, b) shouldBe expected
        }
    }

    data class Extended(val a: Long, val b: Long, val gcd: Long, val x: Long, val y: Long)

    context("gcd extended") {
        withData<Extended>(
            Extended(126, 34, 2, -7, 26),
            Extended(80, 62, 2, 7, -9),
        ) { (a, b, gcd, x, y) ->
            val result = gcdExtended(a, b)
            result.first shouldBe gcd
            result.second shouldBe x
            result.third shouldBe y
            result.first shouldBe x * a + y * b
        }
    }


})
