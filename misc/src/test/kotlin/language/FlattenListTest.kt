package language

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class FlattenListTest : FunSpec({

    test("flatten empty") {
        flatten(emptyList()).shouldBeEmpty()

    }

    test("flatten nested-0") {
        flatten(listOf(1, 2))
            .shouldBe(listOf(1, 2))
    }

    test("flatten nested-1") {
        flatten(listOf(1, 2, listOf(3, 4)))
            .shouldBe(listOf(1, 2, 3, 4))
    }

    test("flatten nested-2") {
        flatten(listOf(1, 2, listOf(3, 4, listOf(5, 6))))
            .shouldBe(listOf(1, 2, 3, 4, 5, 6))
    }

})
