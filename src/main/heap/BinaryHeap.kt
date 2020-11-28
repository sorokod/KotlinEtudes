package heap


import heap.CompleteBinaryTree.IndexType.PARENT

/**
 * https://en.wikipedia.org/wiki/Binary_heap
 */
class BinaryHeap<T : Comparable<T>>(initialCapacity: Int = 100) {

    private val tree = CompleteBinaryTree<T>(initialCapacity)


    fun add(value: T) {
        tree.addLeaf(value)
        bubbleUp(index = size())
    }


    fun remove(): T {
        val result = tree.removeRoot()
        bubbleDown(1)

        return result
    }


    fun isEmpty() = tree.isEmpty()


    fun size() = tree.size()


    override fun toString() = tree.toString()


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
        bubbleUp(tree.indexOf(index, PARENT))
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