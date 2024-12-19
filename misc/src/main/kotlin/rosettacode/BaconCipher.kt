package rosettacode

import kotlin.text.fold


// https://rosettacode.org/wiki/Bacon_cipher#Kotlin

/**
 * Encoding
 * The encoding process takes
 *      the plainText message - the text that we want to hide.
 *      the coverText - the text we want to hide the plainText in
 * and produces the stegoText.
 *
 * Example:
 * plainText = "ab c"
 * coverText = "one two three four fivex"
 *
 * 1. "ab c" is converted to abText using the [codes] table: "AAAAAAAAABBBBAAAAABA"
 * Note that the length of abText = length of plainText x 5
 *
 * 2. We iterated over the coverText matching its characters to abText. We replace
 * the coverText letters with their uppercase variant if the matching abText
 * character is B otherwise we leave it as is.
 *
 * o = A => o  0
 * n = A => n  1
 * e = A => e  2
 *   =>    // index is not incremented
 * t = A => t  3
 * w = A => w  4
 * o = A => o  5
 *   =>    // index is not incremented
 * t = A => t  6
 * h = A => h  7
 * r = A => r  8
 * e = B => E  9
 * e = B => E  10
 *   =>    // index is not incremented
 * f = B => F  11
 * o = B => O  12
 * u = A => u  13
 * r = A => r  14
 *    =>    // index is not incremented
 * f = A => f  15
 * i = A => i  16
 * v = A => v  17
 * e = B => E  18
 * x = A => x  19
 *
 * The resulting stegoText is: one two thrEE FOur fivEx
 * Note that the number of letters in the coverText must be at least the length of abText.
 *
 * Decoding
 *
 * Example:
 * stegoText: one two thrEE FOur fivEx
 *
 * The resulting abString is the same as the one we got during encoding:
 * AAAAAAAAABBBBAAAAABA
 *
 * We split the abString into quintets and reverse map to the original characters
 * using the  [codes] table
 */

object BaconCipher {
    private val codes: Map<Char, String> = mapOf(
        'a' to "AAAAA", 'b' to "AAAAB", 'c' to "AAABA", 'd' to "AAABB", 'e' to "AABAA",
        'f' to "AABAB", 'g' to "AABBA", 'h' to "AABBB", 'i' to "ABAAA", 'j' to "ABAAB",
        'k' to "ABABA", 'l' to "ABABB", 'm' to "ABBAA", 'n' to "ABBAB", 'o' to "ABBBA",
        'p' to "ABBBB", 'q' to "BAAAA", 'r' to "BAAAB", 's' to "BAABA", 't' to "BAABB",
        'u' to "BABAA", 'v' to "BABAB", 'w' to "BABBA", 'x' to "BABBB", 'y' to "BBAAA",
        'z' to "BBAAB", ' ' to "BBBAA"
    )

    fun Map<Char, String>.encode(text: String): String =
        text.fold("") { buffer, ch ->
            buffer + getOrElse(ch) { this[' '] }
        }

    fun Map<Char, String>.decode(text: String): String =
        text.windowed(size = 5, step = 5)
            .fold("") { buffer, chX5 -> buffer + entries.first { it.value == chX5 }.key }

    /**
     * 1. The [plainText] - message to be hidden is converted to an abString where each
     * character in [plainText] is represented by a quintet according to the [codes] table.
     * All non-letter characters are mapped as if they were ' '
     *
     * 2. Each letter in [coverText] is capitalised or not according to the value in abText
     * Every quintet of letters in the output corresponds to a single character in [plainText].
     */
    fun encode(plainText: String, coverText: String): String {

        val abText = codes.encode(plainText.lowercase())

        val sb = StringBuilder()
        var index = 0

        for (c in coverText.lowercase())
            if (c.isLetter()) {
                when (abText[index]) {
                    'A' -> sb.append(c)
                    'B' -> sb.append(c.uppercase())
                }
                index++
                if (index == abText.length) break
            } else {
                sb.append(c)
            }
        return sb.toString()
    }

    fun decode(stegoText: String): String {

        val abString = stegoText
            .fold("") { buffer, ch ->
                buffer + when (ch) {
                    in 'a'..'z' -> 'A'
                    in 'A'..'Z' -> 'B'
                    else -> ""
                }
            }

        return codes.decode(abString)
    }
}

data class BaconTriple(
    val plainText: String,
    val coverText: String,
    val stegoText: String,
    val decodedText: String = plainText
)


fun main() {

    fun encode_decode(plainText: String, coverText: String) {
        println("plainText: $plainText")
        println("coverText: $coverText")

        val stegoText = BaconCipher.encode(plainText, coverText)
        val decodedText = BaconCipher.decode(stegoText)

        println("stegoText: $stegoText")
        println("decodedText: $decodedText")
        println("=============================")
    }

    encode_decode("ab c", "one two three four fivex")
    encode_decode("a b c", "As a second illustration of th")


    val plainText = "the quick brown fox jumps over the lazy dog"
    val coverText = "bacon's cipher is a method of steganography created by francis bacon." +
            "this task is to implement a program for encryption and decryption of " +
            "plaintext using the simple alphabet of the baconian cipher or some " +
            "other kind of representation of this alphabet (make anything signify anything). " +
            "the baconian alphabet may optionally be extended to encode all lower " +
            "case characters individually and/or adding a few punctuation characters " +
            "such as the space."

    encode_decode(plainText, coverText)
}