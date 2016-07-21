package bst

import bst.Bst.*

/**
 * Bst recursive search
 */
fun <T : Comparable<T>> search(tree: Bst<T>, value: T): Bst<T> =
        when (tree) {
            is Empty -> tree
            is Node -> when (value.compareTo(tree.value)) {
                -1 -> search(tree.left, value)
                1 -> search(tree.right, value)
                else -> tree
            }
        }

/**
 * Bst non recursive search
 */
fun <T : Comparable<T>> searchNR(tree: Bst<T>, value: T): Bst<T> {
    var result = tree

    while (result is Node && result.value != value) {
        when (value.compareTo(result.value)) {
            -1 -> result = result.left
            else -> result = result.right
        }
    }

    return result
}


