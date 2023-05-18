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
 * Assume that the [sequence] and its [mcSums] are good.
 * Check that summing with the [candidate] retains the goodness:
 * elements of [mcSums] remain distinct
 */
fun sumsRemainDistinct(candidate: Int, sequence: Iterable<Int>, mcSums: HashSet<Int>): Boolean {
    val candidateSums = mutableListOf<Int>()

    for (s in sequence) {
        when ((candidate + s) !in mcSums) {
            true -> candidateSums.add(candidate + s)
            false -> return false
        }
    }
    with(mcSums) {
        addAll(candidateSums)
        add(candidate + candidate)
    }
    return true
}

fun mianChowla(n: Int): List<Int> {
    val mcSequence = linkedSetOf(1)
    val mcSums = linkedSetOf(1+1)

    var candidate = 2
    while (mcSequence.size < n) {
        if (sumsRemainDistinct(candidate, mcSequence, mcSums)) {
            mcSequence.add(candidate)
        }
        candidate++
    }
    return mcSequence.toList()
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


fun main() {

    mianChowla(100).also {
        println("Mian-Chowla[1..30] = ${it.take(30)}")
        println("Mian-Chowla[91..100] = ${it.drop(90)}")
    }


    measureTimeMillis {
        mianChowla(10).also { println(it) }
    }.also { println(it) }


    measureTimeMillis {
        mianChowlaOriginal(30).also { println(it) }
    }.also { println(it) }
}