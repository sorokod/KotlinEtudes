package rosettacode

import kotlin.system.measureTimeMillis

//https://rosettacode.org/wiki/Mian-Chowla_sequence

//
// The sequence starts with:
//
// A1 = 1
//
// then for n > 1, An is the smallest positive integer such that every pairwise sum
//
// Ai + Aj
//
// is distinct, for all i and j <= n.


/**
 * Assume that the [seq] and its [sums] are good.
 * Check that summing with the [candidate] retains the goodness:
 * elements of [sums] remain distinct
 */
fun sumsRemainDistinct(candidate: Int, seq: Iterable<Int>, sums: MutableSet<Int>): Boolean {
    val candidateSums = mutableListOf<Int>()

    for (s in seq) {
        when ((candidate + s) !in sums) {
            true -> candidateSums.add(candidate + s)
            else -> return false
        }
    }
    with(sums) {
        addAll(candidateSums)
        add(candidate + candidate)
    }
    return true
}

fun mianChowla(n: Int): List<Int> {
    val bufferSeq = linkedSetOf<Int>()
    val bufferSums = linkedSetOf<Int>()

    val sequence = generateSequence(1) { it + 1 } // [1,2,3,..]
        .filter { sumsRemainDistinct(it, bufferSeq, bufferSums) }
        .onEach { bufferSeq.add(it) }

    return sequence.take(n).toList()
}


fun mianChowlaOriginal(n: Int): List<Int> {
    val mc = MutableList(n) { 0 }
    mc[0] = 1
    val hs = mutableSetOf<Int>()
    hs.add(2)
    val hsx = mutableListOf<Int>()
    for (i in 1 until n) {
        hsx.clear()
        var j = mc[i - 1]
        outer@ while (true) {
            j++
            mc[i] = j
            for (k in 0..i) {
                val sum = mc[k] + j
                if (hs.contains(sum)) {
                    hsx.clear()
                    continue@outer
                }
                hsx.add(sum)
            }
            hs.addAll(hsx)
            break
        }
    }
    return mc
}

fun mianChowlaTiming(n: Int) {
    measureTimeMillis {
        mianChowla(n)
    }.also { println("mianChowla($n) in: $it msec.") }

    measureTimeMillis {
        mianChowlaOriginal(n)
    }.also { println("mianChowlaOriginal($n) in: $it msec.") }

}

fun main() {

    mianChowla(10).also {
        println("Mian-Chowla(10) = $it")
    }
//
//
//
//    mianChowla(100).also {
//        println("Mian-Chowla[1..30] = ${it.take(30)}")
//        println("Mian-Chowla[91..100] = ${it.drop(90)}")
//    }

    mianChowlaTiming(300)
}