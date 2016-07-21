import bst.Bst
import bst.Bst.Empty
import bst.Bst.Node
import bst.search
import org.junit.Test

/**
 * Created by xor on 21/07/2016.
 */


class TestBst {

    @Test
    fun testSearch() {

        val tree = Node(5,
                Node(3),
                Node(7,
                        Node(6),
                        Node(9))
        )

        assertNode(search(tree, 3), 3)
        assertNode(search(tree, 6), 6)
        assertEmpty(search(tree, 0))
    }

    private fun assertNode(match: Bst<Int>, value: Int) = match is Node && match.value == value
    private fun assertEmpty(match: Bst<Int>) = match is Empty
}