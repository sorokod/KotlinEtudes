package datastructs.heap

/**
 * A min heap implementation, see: https://en.wikipedia.org/wiki/Min-max_heap
 */
class BinHeap<T : Comparable<T>>(initialCapacity: Int = 100) {

    private val tree = CompleteBinTree<T>(initialCapacity)

    fun add(value: T) = tree.addLeaf(value).also { bubbleUp(index = size()) }

    fun remove(): T = tree.removeRoot().also { bubbleDown(index = 1) }

    fun peek(): T = tree.rootValue()

    fun isEmpty() = tree.isEmpty()

    fun size() = tree.size()


    /**
     * Performs the "bubble up" operation to place a newly inserted element
     * (i.e. the element that is at the size index) in its correct place so
     * that the heap maintains the min-heap order property.
     */
    private tailrec fun bubbleUp(index: Int) {
        if (tree.isRoot(index) || tree.parentValue(index) <= tree.value(index)) {
            return
        }

        tree.swapWithParent(index)
        bubbleUp(tree.indexOfParent(index))
    }

    private tailrec fun bubbleDown(index: Int) {
        val smallestIndex = tree.min(index)
        if (smallestIndex == index) {
            return
        }
        tree.swapWithParent(smallestIndex)
        bubbleDown(smallestIndex)
    }
}


/**
 * A "complete binary tree" with an array representation (see indexOfXXX() functions) . Elements are populated
 * from index = 1
 *
 * It is a binary tree in which all the levels are completely filled except possibly
 * the lowest one, which is filled from the "right".
 */
internal class CompleteBinTree<T : Comparable<T>>(initialCapacity: Int) {

    private var elementCount = 0
    private var array: Array<T?> = arrayOfNulls<Comparable<T>>(initialCapacity) as Array<T?>


    /**
     * Add a leaf as the "last" element
     */
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

    fun isEmpty() = elementCount == 0

    fun size() = elementCount

    private fun ensureCapacity() {
        if (elementCount >= array.lastIndex) {
            array = array.copyOf(array.size * 2)
        }
    }

    fun value(index: Int): T = array[index]!!
    fun rootValue(): T = value(1)
    fun parentValue(index: Int): Comparable<T> = value(indexOfParent(index))
    fun isRoot(i: Int) = i == 1

    internal fun min(index: Int): Int {
        var minIndex = index

        val leftIndex = indexOfLeft(index)
        if (leftIndex <= elementCount && value(leftIndex) < value(minIndex)) {
            minIndex = leftIndex
        }

        val rightIndex = indexOfRight(index)
        if (rightIndex <= elementCount && value(rightIndex) < value(minIndex)) {
            minIndex = rightIndex
        }

        return minIndex
    }


    internal fun swapWithParent(i: Int) {
        val parent = indexOfParent(i)
        val tmp = array[i]
        array[i] = array[parent]
        array[parent] = tmp
    }


    internal fun indexOfParent(current: Int) = current / 2
    internal fun indexOfLeft(current: Int) = current * 2
    internal fun indexOfRight(current: Int) = current * 2 + 1
}