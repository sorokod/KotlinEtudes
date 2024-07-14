package rosettacode

/**
 * Floyd's triangle as defined in Rosetta Code:
 *
 * https://rosettacode.org/wiki/Floyd%27s_triangle#Kotlin
 */

fun doFloyd(rows: Int) {
    val count = rows * (rows + 1) / 2
    val padding = "$count".length
    doFloyd(List(count) { it + 1 }, rows, padding)
}

fun doFloyd(values: List<Int>, row: Int, padding: Int) {
    if (values.isNotEmpty()) {
        val head = values.dropLast(row)
        val tail = values.takeLast(row)

        doFloyd(head, row - 1, padding)

        println(
            tail.joinToString(separator = "") { "%-${padding}d ".format(it) }
        )
    }
}

fun main() {
    listOf(5, 14).forEach { rows ->
        println("### Floyd $rows")
        doFloyd(rows)
    }
}