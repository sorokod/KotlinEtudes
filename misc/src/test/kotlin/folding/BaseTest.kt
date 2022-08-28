package folding

import folding.FoldFunctions.mul
import folding.FoldFunctions.sum
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BaseTest : FunSpec({

    test("fancy list creation") {
        L[1, 2, 3] shouldBe listOf(1, 2, 3)
        L["A", "B", "C"] shouldBe listOf("A", "B", "C")
    }

    test("head/tail on empty list throws exception") {
        shouldThrow<NoSuchElementException> {
            emptyList<Any>().head
        }
        shouldThrow<IndexOutOfBoundsException> {
            emptyList<Any>().tail
        }
    }

    test("GOLD sum") {
        val data = L[1, 2, 3, 4]
        GOLD(sum, 0, data) shouldBe 10
    }

    test("GOLD append") {
        val data = L["A", "B", "C"]
        GOLD({ t, r -> t + r }, "", data) shouldBe "ABC"
    }

    test("GOLD mul") {
        val data = L[1, 2, 3, 4]
        GOLD(mul, 1, data) shouldBe 24
    }

    test("GOLD and / or") {
        val data = L[true, false, true]
        GOLD({ t, r -> t.and(r) }, true, data) shouldBe false
        GOLD({ t, r -> t.or(r) }, false, data) shouldBe true
    }

    //  length = fold (λx n → 1 + n) 0
    test("GOLD length") {
        val data = L[1, 2, 3, 4]
        // we ignore the current element while incrementing the accumulated value
        GOLD({ _, r -> r + 1 }, 0, data) shouldBe data.size
    }

    //  reverse = fold (λx xs → xs ++ [x]) [ ]
    test("GOLD reverse") {
        val data = L[1, 2, 3, 4]
        GOLD({ t: Int, r: List<Int> -> r + t }, listOf(), data) shouldBe L[4, 3, 2, 1]
    }

    //  map f = fold (λx xs → f x : xs) [ ]
    test("GOLD map") {
        val data = L[1, 2, 3, 4]
        GOLD({ t, r -> L[t >= 3] + r }, listOf<Boolean>(), data) shouldBe L[false, false, true, true]
    }

    // filter p = fold (λx xs → if p x then x : xs else xs) [ ]
    test("GOLD filter") {
        val data = L[1, 2, 3, 4]
        val p = { n: Int -> n >= 3 } // predicate

        GOLD({ t, r -> if (p(t)) L[t] + r else r }, listOf<Int>(), data) shouldBe L[3, 4]
    }

    // sumlength = fold (λn (x, y) → (n + x, 1 + y)) (0, 0)
    // produce a tuple / pair of (sum, length)
    test("GOLD sum and length") {
        val data = L[1, 2, 3, 4]
        GOLD({ t, r -> Pair(r.first + t, r.second + 1) }, Pair(0, 0), data) shouldBe Pair(10, 4)
    }


    test("GOLD fibonacci") {
        val data = L[0, 1, 2, 3, 4, 5, 6]

        val nextFib = { n: Int, p: Pair<Int, Int> ->
            when (n) {
                0, 1 -> p
                else -> Pair(p.second, p.second + p.first)
            }
        }
        GOLD(nextFib, Pair(0, 1), data) shouldBe Pair(5, 8)
    }


    test("GOLDL sum") {
        val data = L[1, 2, 3, 4]
        GOLD(sum, 0, data) shouldBe GOLDL(sum, 0, data)
    }

    test("GOLDL append") {
        val data = L["A", "B", "C"]
        GOLDL({ t, r -> t + r }, "", data) shouldBe "CBA"
    }
})
