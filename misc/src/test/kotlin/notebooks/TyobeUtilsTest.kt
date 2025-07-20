package notebooks

import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class TyobeUtilsTest : StringSpec({

    "datesAsYYYYMM should convert valid date formats correctly" {
        forAll(
            row("Oct 2, 2024", "2024-10"),
            row("Jan 15, 2023", "2023-01"),
            row("Dec 31, 2022", "2022-12"),
            row("Feb 1, 2021", "2021-02"),
            row("Jul 4, 1776", "1776-07"),
            row("Mar 5, 2024", "2024-03"),
            row("Aug 9, 2023", "2023-08"),
            row("Apr 15, 2024", "2024-04"),
            row("Nov 28, 2023", "2023-11")
        ) { input, expected ->
            datesAsYYYYMM(input) shouldBe expected
        }
    }

    "datesAsYYYYMM should return original string for invalid formats" {
        forAll(
            row("2024-10-02"),
            row("October 2, 2024"),
            row("invalid date"),
            row(""),
            row("Oct 32, 2024")
        ) { input ->
            datesAsYYYYMM(input) shouldBe input
        }
    }

    "anyToDouble should convert valid numeric values" {
        forAll(
            row("15.5", 15.5),
            row("42", 42.0),
            row("0", 0.0),
            row("3.14159", 3.14159),
            row("-5.7", -5.7),
            row("1000", 1000.0),
            row(42, 42.0),
            row(3.14159, 3.14159),
            row(-5.7f, -5.7),
            row(1000L, 1000.0),
            row("  15.5  ", 15.5),
            row("\t42\n", 42.0),
            row("0.0", 0.0),
            row(".5", 0.5),
            row("5.", 5.0)
        ) { input, expected ->
            anyToDouble(input) shouldBe expected
        }
    }

    "anyToDouble should return 0.0 for invalid inputs" {
        forAll(
            row(null),
            row(""),
            row("abc"),
            row("not a number"),
            row("15.5.5"),
            row("NaN"),
            row("Infinity"),
            row("-Infinity"),
            row(true),
            row(false),
            row("   ")
        ) { input ->
            anyToDouble(input) shouldBe 0.0
        }
    }
})