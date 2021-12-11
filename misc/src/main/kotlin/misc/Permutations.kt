package misc

import java.lang.StringBuilder


fun permutations1(str: String, accumulate: String = ""): List<String> {
    return when (str.isEmpty()) {
        true -> listOf(accumulate)
        false -> {
            str.flatMapIndexed { i, c ->
                permutations1(str.removeRange(i, i + 1), accumulate + c)
            }
        }
    }
}

/**
 * Permute a s String by permuting it's tail and then expanding the result
 * with the head character.
 */
fun permutations(str: String): List<String> {
    return when (str.isEmpty()) {
        true -> listOf("")
        false -> {
            val head = str.first()
            val tail = str.drop(1)
            val accumulate = permutations(tail)

            accumulate.flatMap { permutation -> permutation.expandWith(head) }
        }
    }
}


/**
 * Expands this String with some Char e.g:
 *      "23".expandWith('1') => [123 213 231]
 *      "".expandWith('1')   => [1]
 */
inline fun String.expandWith(extra: Char): List<String> {
    val result: List<String> = indices.map { idx ->
        StringBuilder(this).insertRange(idx, extra.toString(), 0, 1).toString()
    }
    return result + listOf(this + extra)
}


fun main() {
    println(permutations("123")) // [123, 213, 231, 132, 312, 321]
    println(permutations("12"))  // [12, 21]
    println(permutations("1"))   // [1]
    println(permutations(""))    // []

    println(permutations1("123")) // [123, 132, 213, 231, 312, 321]
    println(permutations1("12"))  // [12, 21]
    println(permutations1("1"))   // [1]
    println(permutations1(""))    // []

}
