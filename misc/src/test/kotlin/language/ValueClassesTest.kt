package language

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.util.UUID


class ValueClassesTest : FunSpec({

    test("Equality behaves as expected") {
        XId.from("hi") shouldBe XId.from("hi")
        XId.from("hi").hashCode() shouldBe XId.from("hi").hashCode()

        XId.from("hi") shouldNotBe XId.from("by")
    }

    test("Hashcode is shared with shared value") {
        XId.from("hi").hashCode() shouldBe YId.from("hi").hashCode()
    }


    test("Sets of values behave as expected") {
        val id = UUID.randomUUID()

        setOf(XId(id), XId(id)) shouldHaveSize 1
        setOf(XId(id), YId(id)) shouldHaveSize 2
    }

    test("Companion.from(String) works as expected") {
        val helloUUID = UUID.nameUUIDFromBytes("hello".toByteArray())

        XId(helloUUID) shouldBe XId.from("hello")
        YId(helloUUID) shouldBe YId.from("hello")
    }
})
