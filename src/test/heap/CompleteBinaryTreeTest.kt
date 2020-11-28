package heap

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CompleteBinaryTreeTest {

    private val capacity = 10
    private val subject = CompleteBinaryTree<Int>(capacity)

    private val values_3 = IntArray(3) { i -> i + 1 }
    private val values_11 = IntArray(capacity + 1) { i -> i + 1 }

    @Test
    fun `creation test`() {
        assertTrue(subject.isEmpty())
        assertTrue(subject.isRoot(1))
    }

    @Test
    fun `addLeaf() test`() {
        values_3.forEach { subject.addLeaf(it) }

        assertFalse(subject.isEmpty())
        values_3.forEach { assertEquals(it, subject.value(it)) }
    }

    @Test
    fun `value() and capacity test`() {
        values_11.forEach { subject.addLeaf(it) }
        values_11.forEachIndexed { i, value -> assertEquals(value, subject.value(i+1)) }
    }


    @Test
    fun `removeRoot() test`() {
        values_3.forEach { subject.addLeaf(it) } // [null, 1, 2, 3, null,...

        subject.removeRoot().let { // [null, 3, 2, null, ...
            assertEquals(1, it)
            assertEquals(3, subject.value(1))
            assertEquals(2, subject.value(2))
        }

        subject.removeRoot().let {// [null, 2, null, ...
            assertEquals(3, it)
            assertEquals(2, subject.value(1))
        }

        subject.removeRoot().let {// [null, null, ...
            assertEquals(2, it)
            assertTrue(subject.isEmpty())
        }
    }

}