package heap

import heap.CompleteBinaryTree.IndexType.*
import java.lang.IllegalStateException

/**
 * A "complete binary with" an array representation. Elements are populated from index = 1
 *
 * A complete binary tree is a binary tree in which all the levels are completely filled except possibly
 * the lowest one, which is filled from the "left".
 */
internal class CompleteBinaryTree<T : Comparable<T>>(initialCapacity: Int) {

    enum class IndexType { LEFT, RIGHT, PARENT }

    internal var elementCount = 0
    private var array: Array<T?> = arrayOfNulls<Comparable<T>>(initialCapacity) as Array<T?>


    fun addLeaf(value: T) {
        ensureCapacity()
        array[++elementCount] = value
    }


    /**
     * Returns the value in the root and replaces it with the "last" element
     */
    fun removeRoot(): T = rootValue().also {
        array[1] = array[elementCount]
        array[elementCount--] = null
    }

    fun isEmpty() = size() == 0

    fun size() = elementCount

    fun isRoot(i: Int) = i == 1

    override fun toString() = array.contentToString()


    fun indexOf(current: Int, type: IndexType): Int =
            when (type) {
                LEFT -> current * 2
                RIGHT -> current * 2 + 1
                PARENT -> current / 2
            }


    fun value(index: Int): T = array[index]!!


    fun parentValue(index: Int): Comparable<T> = value(indexOf(index, PARENT))


    internal fun swapWithParent(i: Int) {
        val parent = indexOf(i, PARENT)
        val tmp = array[i]
        array[i] = array[parent]
        array[parent] = tmp
    }


    internal fun min(index: Int): Int {
        var smallestIndex = index

        val leftIndex = indexOf(index, LEFT)
        if (leftIndex <= elementCount && value(leftIndex)!! < value(smallestIndex)!!) {
            smallestIndex = leftIndex
        }

        val rightIndex = indexOf(index, RIGHT)
        if (rightIndex <= elementCount && value(rightIndex)!! < value(smallestIndex)!!) {
            smallestIndex = rightIndex
        }

        return smallestIndex
    }

    private fun rootValue(): T =
            when {
                isEmpty() -> throw IllegalStateException("Heap is empty")
                else -> array[1] as T
            }


    private fun ensureCapacity() {
        if (elementCount >= array.lastIndex) {
            array = array.copyOf(array.size * 2)
        }
    }
}