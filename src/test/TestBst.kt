import bst.Bst
import bst.Bst.Empty
import bst.Bst.Node
import bst.*
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
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
        assertEquals("Unexpected max depth", 2,  tree0.depth { x, y -> max(x,y)  } )
        assertEquals("Unexpected min depth", 1,  tree0.depth { x, y -> min(x,y)  } )
    }

    private fun assertNode(match: Bst<Int>, value: Int) = assertTrue( match is Node && match.value == value )
    private fun assertEmpty(match: Bst<Int>) = assertTrue( match is Empty )
}