package language.functional

fun <T : Comparable<T>> List<T>.quickSort(): List<T> =
        if(size < 2) this
        else {
            val pivot = first()
            val (smaller, greater) = drop(1).partition { it <= pivot}
            smaller.quickSort() + pivot + greater.quickSort()
        }


fun main(args: Array<String>) {
    println( listOf(2,5,1).quickSort() )
}