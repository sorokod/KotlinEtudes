package bst

sealed class Bst<out T> {
    class Node<out T>(val value: T, val left: Bst<T> = Empty, val right: Bst<T> = Empty) : Bst<T>()
    object Empty : Bst<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Empty -> "Bst.Empty"
            is Node -> "Bst.Node[${value}]"
        }
    }
}




