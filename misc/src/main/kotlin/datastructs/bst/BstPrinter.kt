package datastructs.bst

import datastructs.bst.Bst.Node

fun <T> traversePreOrder(root: Bst<T>): CharSequence {

    return when (root) {
        is Bst.Empty -> ""
        is Node -> {
            val sb = StringBuilder(root.value.toString())

            val pointerRight = "└──"
            val pointerLeft = if (root.right is Node) "├──" else "└──"
            traverseNodes(sb, "", pointerLeft, root.left, root.right is Node)
            traverseNodes(sb, "", pointerRight, root.right, false)
            sb
        }
    }

}

private fun <T> traverseNodes(
    sb: StringBuilder, padding: String, pointer: String, node: Bst<T>,
    hasRightSibling: Boolean
) {
    if (node is Node) {
        sb.append("\n$padding$pointer${node.value}")

        val paddingForBoth = padding + if (hasRightSibling) "│  " else "   "

        val pointerRight = "└──"
        val pointerLeft = if (node.right is Node) "├──" else "└──"

        traverseNodes(sb, paddingForBoth, pointerLeft, node.left, node.right is Node)
        traverseNodes(sb, paddingForBoth, pointerRight, node.right, false)
    }
}


fun main() {
    /**
    5
    3      7
    6     9
     **/
    val tree0 = Node(5,
        Node(3),
        Node(7,
            Node(6),
            Node(9)
        )
    )

    print(tree0)

    println(traversePreOrder(tree0))


    val tree1 = Node(
        "root",
        Node(
            "node1",
            Node(
                "node3",
                Node(
                    "node7",
                    Node("node8"),
                    Node("node9")
                )
            ),
            Node("node4")
        ),
        Node(
            "node2",
            Node("node5"),
            Node("node6")
        )
    )

    println(traversePreOrder(tree1))


}