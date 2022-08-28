package tuples

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeTypeOf
import io.kotest.matchers.types.shouldNotBeTypeOf

class TupleTest : FunSpec({

    test("tuple with explicit type") {
        val (t1, t2) = Tuple2("tiger", 43)

        t1 shouldBe "tiger"
        t2 shouldBe 43
    }

    test("tuple with implicit type") {
        val (t1, t2) = Tuple.of("tiger", 43)

        t1 shouldBe "tiger"
        t2 shouldBe 43
    }

    test("explicitly typed tuples = implicitly typed tuples") {
        listOf(
            Tuple1("tiger") to Tuple.of("tiger"),
            Tuple2("tiger", 42) to Tuple.of("tiger", 42),
            Tuple3("tiger", 42, 1.0) to Tuple.of("tiger", 42, 1.0),
            Tuple4("tiger", 42, 1.0, true) to Tuple.of("tiger", 42, 1.0, true),
            Tuple5("tiger", 42, 1.0, true, 'z') to Tuple.of("tiger", 42, 1.0, true, 'z')
        ).forAll { (explicitlyTypedTuple, implicitlyTypedTuple) ->
            explicitlyTypedTuple shouldBe implicitlyTypedTuple
        }
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
        Tuple.of("tiger", 43)
            .shouldBeInstanceOf<Tuple>()
            .shouldBeTypeOf<Tuple2<*, *>>()
            .shouldNotBeTypeOf<Tuple3<*, *, *>>()
    }
})
