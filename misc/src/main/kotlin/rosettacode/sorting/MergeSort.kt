package rosettacode.sorting

import kotlin.math.ceil

fun split(list: List<Int>): List<List<Int>> =
    ceil(list.size / 2.0).toInt().let { idx -> list.chunked(idx) }


fun merge(left: List<Int>, right: List<Int>): List<Int> = mutableListOf<Int>().apply {
    var iLeft = 0
    var iRight = 0

    while (iLeft in left.indices && iRight in right.indices) {
        when (left[iLeft] <= right[iRight]) {
            true -> add(left[iLeft++])
            false -> add(right[iRight++])
        }
    }

    while (iLeft in left.indices) {
        add(left[iLeft++])
    }

    while (iRight in right.indices) {
        add(right[iRight++])
    }
}

fun mSort(list: List<Int>): List<Int> =
    when (list.size < 2) {
        true -> list
        else -> {
            val (left, right) = split(list)
            merge(mSort(left), mSort(right))
        }
    }

fun main(args: Array<String>) {
    val numbers = listOf(5, 2, 3, 17, 12, 1, 8, 3, 4, 9, 7)
    println("Unsorted: $numbers")
    println("Sorted  : ${mSort(numbers)}")
}
