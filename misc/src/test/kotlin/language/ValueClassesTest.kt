package language

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.util.UUID


class ValueClassesTest : FunSpec({

    val id1 = UUID.randomUUID()
    val id2 = UUID.randomUUID()

    test("Equality Identity and Hash code") {
        UserId(id1) shouldBe UserId(id1)
        UserId(id1).hashCode() shouldBe UserId(id1).hashCode()

        UserId(id1).hashCode() shouldNotBe UserId(id2).hashCode()

        UserId(id1) shouldNotBe CustomerId(id1)
    }

    test("Sets behave as expected") {
        setOf(UserId(id1),UserId(id1)) shouldHaveSize 1
    }

    test("Companion objects are supported") {
        CustomerId.rand() shouldNotBe CustomerId.rand()
        CustomerId.from("Alice") shouldNotBe CustomerId.from("Bob")
    }

    test("Functions are supported") {
        CustomerId.rand().versionAndVariant() shouldBe Pair(4,2)
        CustomerId.from("Alice").versionAndVariant() shouldBe Pair(3,2)
    }

})
