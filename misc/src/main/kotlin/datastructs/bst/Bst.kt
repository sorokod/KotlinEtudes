package datastructs.bst

sealed class Bst<out T> {
    class Node<out T>(
        val value: T,
        val left: Bst<T> = Empty,
        val right: Bst<T> = Empty
    ) : Bst<T>()

    object Empty : Bst<Nothing>()


    /**
     *
     */
    override fun toString(): String {
        return when (this) {
            is Empty -> "Bst.Empty"
            is Node -> "Bst.Node[${value}]"
        }
    }
}


/**
 * The minimal or maximal tree depth
 */
fun <T> Bst<T>.depth(minORmax: (Int, Int) -> Int): Int {
    fun <T> depth(bst: Bst<T>, minORmax: (Int, Int) -> Int): Int =
        when (bst) {
            is Bst.Empty -> -1
            is Bst.Node -> {
                val lDepth = depth(bst.left, minORmax)
                val rDepth = depth(bst.right, minORmax)
                minORmax(lDepth, rDepth) + 1
            }
        }

    return depth(this, minORmax)
}

/**
 * Traverse in pre-order
 */
fun <T> Bst<T>.preorder(handler: (Bst.Node<T>) -> Unit) {
    if (this is Bst.Node) {
        handler(this)
        this.left.preorder(handler)
        this.right.preorder(handler)
    }
}

