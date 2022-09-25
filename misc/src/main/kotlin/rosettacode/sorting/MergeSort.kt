package rosettacode.sorting

import kotlin.math.ceil

/**
 * Split [list] into two (more or less) equal sized lists.
 */
fun split(list: List<Int>): List<List<Int>> =
    ceil(list.size / 2.0).toInt().let { idx -> list.chunked(idx) }


/**
 * Merge the sorted [left] and [right] lists into one sorted list.
 */
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
