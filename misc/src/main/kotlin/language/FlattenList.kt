package language

/**
 * Returns a flattened [nestedList].
 * for example:
 * [1, 2, [3, 4, [5, 6] ] ] -> [1, 2, 3, 4, 5, 6]
 */
fun flatten(nestedList: List<Any>): List<Any> {
    val result = mutableListOf<Any>()

    fun flattenInternal(list: List<Any>) {
        for (e in list) {
            when (e is List<*>) {
                true -> flattenInternal(e as List<Any>)
                false -> result.add(e)
            }
        }
    }

    flattenInternal(nestedList)
    return result
}

fun main() {


    val l1 = listOf<Int>()
    val l2 = listOf(1, 2)
    val l3 = listOf(1, 2, listOf(3, 4))
    val l4 = listOf(1, 2, listOf(3, 4, listOf(5, 6)))

    flatten(l1).also { println(it) }
    flatten(l2).also { println(it) }
    flatten(l3).also { println(it) }
    flatten(l4).also { println(it) }

}