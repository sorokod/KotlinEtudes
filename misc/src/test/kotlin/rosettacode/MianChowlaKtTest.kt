package rosettacode

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MianChowlaKtTest : FunSpec({

    val expected_91_100 = listOf(22526, 23291, 23564, 23881, 24596, 24768, 25631, 26037, 26255, 27219)

    val expected_1_30 = listOf(
        1,
        2,
        4,
        8,
        13,
        21,
        31,
        45,
        66,
        81,
        97,
        123,
        148,
        182,
        204,
        252,
        290,
        361,
        401,
        475,
        565,
        593,
        662,
        775,
        822,
        916,
        970,
        1016,
        1159,
        1312
    )

    test("mianChowla 1-30") {
        mianChowlaOriginal(30) shouldBe expected_1_30
        mianChowla(30) shouldBe expected_1_30
    }

    test("mianChowla 90-100") {
        mianChowlaOriginal(100).subList(90, 100) shouldBe expected_91_100
        mianChowla(100).subList(90, 100) shouldBe expected_91_100
    }

})
