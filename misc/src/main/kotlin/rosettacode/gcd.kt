package rosettacode

import kotlin.math.abs

/**
 * Recursive
 */
tailrec fun gcdR(a: Int, b: Int): Int =
    when (b == 0) {
        true -> abs(a)
        false -> gcdR(b, a % b)
    }


fun main() {

    val pairs = listOf(
        Pair(0, 0),
        Pair(10, 35),
        Pair(10, 31),
        Pair(120, 18),
    )

    pairs.onEach { pair ->
        gcdR(pair.first, pair.second)
            .also { println("gcd(${pair.first},${pair.second}) = $it") }
    }
}