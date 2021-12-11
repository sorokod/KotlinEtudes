package misc

/**
 * A recursive impl of Run Length Encoding (from Rosetta Code)
 *
 */
tailrec fun rle_r(text: String, accumulated: String = ""): String {
    if (text.isEmpty()) {
        return accumulated
    }
    val firstChar = text[0]
    val count = text.takeWhile { it == firstChar }.length

    return rle_r(text.substring(count), accumulated + "$count$firstChar")
}


/**
 * A non-recursive impl of Run Length Encoding (from Rosetta Code)
 *
 */
fun rle(text: String): String {
    val accumulated = StringBuilder()
    val n = text.length
    var i = 0

    while (i < n) {
        var ctr = 1
        while (i < n - 1 && (text[i] == text[i + 1])) {
            ctr++
            i++
        }
        accumulated.append("$ctr${text[i]}")
        i++
    }
    return accumulated.toString()
}
