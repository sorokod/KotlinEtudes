package tuples

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeTypeOf
import io.kotest.matchers.types.shouldNotBeTypeOf

class TupleTest : FunSpec({

    test("create tuple with explicit type") {
        val (e1, e2) = Tuple2("tiger", 43)

        e1 shouldBe "tiger"
        e2 shouldBe 43
    }

    test("tuples play more or less nicely with when") {

        fun isTup1(tup: Tuple) = when (tup) {
            is Tuple1<*> -> true
            else -> false
        }

        isTup1(Tuple.of("tiger")).shouldBeTrue()
        isTup1(Tuple.of("tiger", 42)).shouldBeFalse()
    }

    test("create tuple with implicit type") {
        Tuple.of("tiger", 43) shouldBe Tuple2("tiger", 43)

        Tuple.of("tiger", 43)
            .shouldBeInstanceOf<Tuple>()
            .shouldBeTypeOf<Tuple2<*, *>>()
            .shouldNotBeTypeOf<Tuple3<*, *, *>>()
    }
})
