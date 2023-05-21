package rosettacode

//https://rosettacode.org/wiki/Split_a_character_string_based_on_change_of_character#Kotlin

fun splitOnChange(src: String): String =
    src.fold("") { acc, c ->
        if (acc.isEmpty() || acc.last() == c) "$acc$c" else "$acc, $c"
    }


fun splitOnChangeOriginal(s: String): String {
    if (s.length < 2) return s
    var t = s.take(1)
    for (i in 1 until s.length)
        if (t.last() == s[i]) t += s[i]
        else t += ", " + s[i]
    return t
}

fun main() {
    splitOnChange("""gHHH5YY++///\""").also { println(it)}  // g, HHH, 5, YY, ++, ///, \
//    val s = """gHHH5YY++///\"""
//    println(splitOnChangeOriginal(s)) // g, HHH, 5, YY, ++, ///, \
//    println(splitOnChange(s)) // g, HHH, 5, YY, ++, ///, \
}