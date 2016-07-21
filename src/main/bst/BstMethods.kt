package bst

import bst.Bst.*

/**
 *
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

