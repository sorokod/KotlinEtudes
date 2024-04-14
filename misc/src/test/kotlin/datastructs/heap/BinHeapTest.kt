package datastructs.heap

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class BinHeapTest : StringSpec({

    /**
     * Repeatedly remove the root element from the [heap] into a list until the heap is empty.
     */
    fun <T : Comparable<T>> _drainHeap(heap: BinHeap<T>): List<T> =
        List(heap.size) { heap.remove() }


    fun <T : Comparable<T>> BinHeap<T>.fillWith(data: Array<T>) =
        data.onEach { this.add(it) }

    fun _dataInts(size: Int = 100): Array<Int> =
        Array(size) { Random.nextInt(-1_000, 1_000) }

    fun _dataStrings(): Array<String> =
        arrayOf("fifteen", "five", "eleven", "three", "four", "eight", "a", "bb")

    "peek strings" {
        val heap = BinHeap<String>().also {
            it.fillWith(_dataStrings())
        }

        heap.peek() shouldBe "a"
    }

    "peek ints 7" {
        val heap = BinHeap<Int>()
        heap.add(7)

        heap.peek() shouldBe 7
    }

    "peek ints -7" {
        val heap = BinHeap<Int>().also { it.fillWith(arrayOf(-7, 7)) }

        heap.peek() shouldBe -7
    }

    "peek ints 100" {
        val data = _dataInts(100)
        val heap = BinHeap<Int>().also { it.fillWith(data) }

        val expectedMin = data.minOrNull()!!

        heap.peek() shouldBeEqual expectedMin
    }

    "peek ints sort" {
        val data = _dataInts(10)
        val heap = BinHeap<Int>().also { it.fillWith(data) }


        val actual = _drainHeap(heap)
        actual shouldBe data.sorted()
    }

    "properties" {

        with(BinHeap<String>()) {
            empty shouldBe true
            size shouldBe 0
        }

        with(BinHeap<Int>().also { it.add(1) }) {
            empty shouldBe false
            size shouldBe 1
        }

        with(BinHeap<Int>().also { it.fillWith(arrayOf(1, 1)) }) {
            empty shouldBe false
            size shouldBe 2
        }

        with(BinHeap<Int>().also { it.fillWith(_dataInts(100)) }) {
            empty shouldBe false
            size shouldBe 100
        }
    }


})
