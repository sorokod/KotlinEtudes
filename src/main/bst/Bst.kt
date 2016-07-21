package bst

sealed class Bst<out T> {
    class Node<out T>(val value: T, val left: Bst<T> = Empty, val right: Bst<T> = Empty) : Bst<T>()
    object Empty : Bst<Nothing>()


    fun depth(minmax: (Int, Int) -> Int): Int = depth(this, minmax)


    private fun <T> depth(bst: Bst<T>, minmax: (Int, Int) -> Int): Int =
            when (bst) {
                is Empty -> -1
                is Node -> {
                    val lDepth = depth(bst.left, minmax)
                    val rDepth = depth(bst.right, minmax)
                    minmax(lDepth, rDepth) + 1
                }
            }


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




