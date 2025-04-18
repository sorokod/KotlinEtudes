package rosettacode

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SplitOnChangeTest : FunSpec({

    test("splitOnChange") {
        splitOnChange("""gHHH5YY++///\""") shouldBe """g, HHH, 5, YY, ++, ///, \"""

        splitOnChange("3.1415") shouldBe "3, ., 1, 4, 1, 5"

        splitOnChange("") shouldBe ""
    }
})
