package rosettacode

/**
 * A recursive impl of Run Length Encoding
 *
 */
tailrec fun rle(text: String, accumulated: String = ""): String =
    when (text.isEmpty()) {
        true -> accumulated
        false -> {
            val firstChar = text.first()
            val count = text.takeWhile { it == firstChar }.length
            rle(text.substring(count), accumulated + "$count$firstChar")
        }
    }

