package rosettacode

import kotlin.math.abs

/**
 * Recursive
 */
tailrec fun gcdR(a: Long, b: Long): Long =
    when (b == 0L) {
        true -> abs(a)
        false -> gcdR(b, a % b)
    }

fun lcm(a: Long, b: Long): Long = a * b / gcdR(a,b)

/**
 * Calculate GCD(a,b) along with Bezout roots X, Y such that GCD = Xa + Yb
 * @return a triple (GCD,  )
 * see:
 * https://math.stackexchange.com/questions/85830/how-to-use-the-extended-euclidean-algorithm-manually/85841#85841
 */
fun gcdExtended(a: Long, b: Long) : Triple<Long, Long, Long> {
    var aa = a
    var bb = b

    var prevX: Long = 1
    var prevY: Long = 0

    var X: Long = 0
    var Y: Long = 1

    var temp: Long

    while (bb != 0L) {
        val q = aa / bb
        val r = aa % bb

//        println("   => $aa = $q * $bb  + $r")

        aa = bb
        bb = r

        temp = X
        X = prevX - q * X
        prevX = temp

        temp = Y
        Y = prevY - q * Y
        prevY = temp

//        println("   ==> x=$prevX y=$prevY")

    }
    println("GCD($a,$b) = $aa. Roots  x= $prevX y= $prevY")

    return Triple(aa, prevX, prevY)
}


fun main() {

//    val pairs = listOf(
//        Pair(0L, 0L),
//        Pair(10L, 35L),
//        Pair(10L, 31L),
//        Pair(120L, 18L),
//    )
//
//    pairs.onEach { pair ->
//        gcdR(pair.first, pair.second)
//            .also { println("gcd(${pair.first},${pair.second}) = $it") }
//    }

    gcdExtended(126, 34)
    gcdExtended(80, 62)
}