package language

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.util.UUID


class ADateTest : FunSpec({

    test("ADate creation and access") {

        with(ADate(2023, 11, 9)) {
            year shouldBe 2023
            month shouldBe 11
            day shouldBe 9
        }
    }
})
