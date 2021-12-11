package datastructs.heap

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random

class BinHeapTest {

    @Test
    fun `random Int test`() {
        val heap = BinHeap<Int>()
        val data = Array(1_000) { Random.nextInt(-1_000, 1_000) }

        // add data to the heap
        data.forEach { heap.add(it) }

        let { // peek() test
            val expected = data.minOrNull()
            val actual = heap.peek()
            assertEquals(expected, actual)
        }

        let { // sorted test
            val expected = data.sorted()
            val actual = drainHeap(heap)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `strings test`() {
        val heap = BinHeap<String>()
        val data = arrayOf("fifteen", "five", "eleven", "three", "four", "eight", "a", "bb")

        // add data to the heap
        data.forEach { heap.add(it) }

        let { // peek() test
            val expected = "a"
            val actual = heap.peek()
            assertEquals(expected, actual)
        }

        let { // sorted test
            val expected = data.sorted()
            val actual = drainHeap(heap)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `readme test`() {
        val minHeap = BinHeap<Int>()
        val data = Array(1_000) { Random.nextInt(-1_000, 1_000) }

        // add data to heap
        data.forEach { minHeap.add(it) }.apply {
            assertTrue(minHeap.size() == data.size)
        }

        // remove all the elements from the heap and verify that the
        // result is sorted
        List(minHeap.size()) { minHeap.remove() }.apply {
            assertTrue(minHeap.isEmpty())
            assertEquals(data.sorted(), this)
        }
    }

    /**
     * Repeatedly removes the root element from the heap into a list, until the heap is empty.
     */
    private fun <T : Comparable<T>> drainHeap(heap: BinHeap<T>) = List(heap.size()) { heap.remove() }
}