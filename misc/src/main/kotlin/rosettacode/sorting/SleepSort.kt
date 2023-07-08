package rosettacode.sorting

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Using coroutines rather than threads
 *
 * https://rosettacode.org/wiki/Sorting_algorithms/Sleep_sort#Using_coroutines
 */
fun sleepSort(list: List<Int>, delta: Long) {
    runBlocking {
        list.onEach {
            launch {
                delay(it * delta)
                print("$it ")
            }
        }
    }
}

fun main() {
    val list = listOf(5, 7, 2, 4, 1, 8, 0, 3, 9, 6)
    println("Unsorted: ${list.joinToString(" ")}")
    print("Sorted  : ")
    sleepSort(list, 10)
}
