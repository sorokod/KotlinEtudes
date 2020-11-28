package heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.time.measureTime

internal class BinaryHeapTest {

    private val random = Random()
    private val subjectString = BinaryHeap<String>()
    private val subjectInt = BinaryHeap<Int>()

    @Test
    fun `test readme`() {
        val heap = BinaryHeap<Int>()
        val randomData = Array(1_000) { random.nextInt(100) }

        // add data to heap
        randomData.onEach { value -> heap.add(value) }

        // remove (sorted) data from heap
        val sorted = mutableListOf<Int>()
        while(!heap.isEmpty()) {
            sorted.add(heap.remove())
        }

        assertEquals(sorted, randomData.toList().sorted())
    }


    @Test
    fun `test heap with strings`() {
        val data = arrayOf("fifteen", "five", "eleven", "three", "four", "eight", "a", "bb")
                .onEach { value -> subjectString.add(value) }

        val sortedData = drainHeap(subjectString)
        assertSorted(sortedData, data)
    }


    @Test
    fun `test heap`() {

        val data = arrayOf(15, 5, 11, 3, 4, 8).onEach { value -> subjectInt.add(value) }

        val sortedData = drainHeap(subjectInt)
        assertSorted(sortedData, data)
    }


    @Test
    fun `test heap 1000`() {

        val data = randomInts(count = 1_000).onEach { value -> subjectInt.add(value) }

        val sortedData = drainHeap(subjectInt)
        assertSorted(sortedData, data)
    }


    @Test
    fun `test heap 1_000_000`() {

        val data = randomInts(count = 1_000_000)

        measureTime {
            data.onEach { value -> subjectInt.add(value) }
        }.also { println("test heap 1_000_000 add duration $it") }

        measureTime {
            val sortedData = drainHeap(subjectInt)
            assertSorted(sortedData, data)
        }.also { println("test heap 1_000_000 remove duration $it") }
    }


    private fun <T : Comparable<T>> drainHeap(heap: BinaryHeap<T>) = Array<Comparable<T>>(heap.size()) { heap.remove() }

    private fun randomInts(count: Int): Array<Int> = Array(count) { random.nextInt(1000) }

    private fun <T : Comparable<T>> assertSorted(sortedData: Array<T>, data: Array<out T>) {
        data.sort()
        assertTrue(
                data.contentEquals(sortedData),
                "expected: ${data.contentToString()} \n\tgot: ${sortedData.contentToString()}"
        )

    }
}