import bst.Bst
import bst.Bst.Empty
import bst.Bst.Node
import bst.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Math.max
import java.lang.Math.min


class TestBst {

    val tree0 = Node(5,
            Node(3),
            Node(7,
                    Node(6),
                    Node(9))
    )

    @Test
    fun testSearch() {

        assertNode(search(tree0, 3), 3)
        assertNode(search(tree0, 6), 6)
        assertEmpty(search(tree0, 0))
        assertEmpty(search(Empty, 3))
    }

    @Test
    fun testNRSearch() {

        assertNode(searchNR(tree0, 3), 3)
        assertNode(searchNR(tree0, 6), 6)
        assertEmpty(searchNR(tree0, 0))
        assertEmpty(searchNR(Empty, 3))
    }

    @Test
    fun testDepth() {
        assertEquals(2, tree0.depth { x, y -> max(x, y) }, "Unexpected max depth")
        assertEquals(1, tree0.depth { x, y -> min(x, y) }, "Unexpected min depth")
    }

    private fun assertNode(match: Bst<Int>, value: Int) = assertTrue(match is Node && match.value == value)
    private fun assertEmpty(match: Bst<Int>) = assertTrue(match is Empty)
}