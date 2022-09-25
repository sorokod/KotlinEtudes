package rosettacode

/**
 * A recursive implementation of Run Length Encoding (RLE)
 */


tailrec fun rle(str: String, accumulated: String = ""): String =
    when (str.isEmpty()) {
        true -> accumulated
        false -> {
            val firstChar = str.first()
            val count = str.stepWhile { it == firstChar }
            rle(str.substring(count), accumulated + "$count$firstChar")
        }
    }

/**
 * Step along the string as long as the [predicate] is true.
 *
 * @return the number of steps performed.
 */
private fun String.stepWhile(predicate: (Char) -> Boolean): Int {
    var i = 0
    while (i in this.indices) {
        when(predicate(this[i])) {
            true -> i++
            false -> return i
        }
    }
    return i
}
