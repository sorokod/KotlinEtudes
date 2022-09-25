package rosettacode.sorting

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MergeSortTest : FunSpec({

    test("should sort") {
        val unsorted = listOf(5, 2, 3, 17, 12, 1, 8, 3, 4, 9, 7)

        mSort(unsorted) shouldBe unsorted.sorted()
    }

})
