package rosettacode


/**
 * Neat case of fold() being just the right tool.
 *
 * https://rosettacode.org/wiki/Split_a_character_string_based_on_change_of_character#Kotlin
 */
fun splitOnChange(str: String): String =
    str.fold("") { result, ch ->
        if (result.isEmpty() || result.last() == ch) "$result$ch" else "$result, $ch"
    }


fun main() {
    val str = """gHHH5YY++///\"""

    splitOnChange(str).also {
        println(it)
        check(it == """g, HHH, 5, YY, ++, ///, \""")
    }
}