package tuples

// Demo in:
// https://github.com/sorokod/KotlinEtudes/blob/master/misc/src/test/kotlin/tuples/TupleTest.kt

sealed class Tuple {
    companion object of {
        operator fun <A> invoke(_1: A): Tuple1<A> =
            Tuple1(_1)

        operator fun <A, B> invoke(_1: A, _2: B): Tuple2<A, B> =
            Tuple2(_1, _2)

        operator fun <A, B, C> invoke(_1: A, _2: B, _3: C): Tuple3<A, B, C> =
            Tuple3(_1, _2, _3)

        operator fun <A, B, C, D> invoke(_1: A, _2: B, _3: C, _4: D): Tuple4<A, B, C, D> =
            Tuple4(_1, _2, _3, _4)

        operator fun <A, B, C, D, E> invoke(_1: A, _2: B, _3: C, _4: D, _5: E): Tuple5<A, B, C, D, E> =
            Tuple5(_1, _2, _3, _4, _5)
    }
}

data class Tuple1<out A>(val _1: A) : Tuple()
data class Tuple2<out A, out B>(val _1: A, val _2: B) : Tuple()
data class Tuple3<out A, out B, out C>(val _1: A, val _2: B, val _3: C) : Tuple()
data class Tuple4<out A, out B, out C, out D>(val _1: A, val _2: B, val _3: C, val _4: D) : Tuple()
data class Tuple5<out A, out B, out C, out D, out E>(val _1: A, val _2: B, val _3: C, val _4: D, val _5: E) : Tuple()

