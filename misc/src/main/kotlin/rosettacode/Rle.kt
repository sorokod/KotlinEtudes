package rosettacode

/**
 * A recursive implementation of Run Length Encoder (RLE)
 */

fun rle(str: String): String {
    if (str.isEmpty()) return ""

    val count = str.takeWhile { it == str.first() }.length
    return "${count}${str.first()}" + rle(str.substring(count))
}
