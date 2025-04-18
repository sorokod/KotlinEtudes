package rosettacode

import org.apache.commons.math3.primes.Primes

//https://rosettacode.org/wiki/Smith_numbers#Kotlin

fun sumDigits(n: Int): Int =
    n.toString().map { it.digitToInt() }.sum()

fun isSmith(n: Int): Boolean {
    if (n < 2) return false
    val factors = Primes.primeFactors(n)

    return when (factors.size) {
        1 -> false // primes don't count
        else -> sumDigits(n) == factors.sumOf { sumDigits(it) }
    }
}

fun main(args: Array<String>) {
    println("The Smith numbers below 10000 are:\n")

    val smiths = (2..9999)
        .filter { isSmith(it) }
        .onEach { print("%5d".format(it)) }

    println("\n\n${smiths.size} numbers found (should be 376)")

}