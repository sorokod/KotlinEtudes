package misc

import kotlin.random.Random

typealias Alphabet = CharArray

/**
 * Given an Alphabet, returns a function that
 * given an int N return a list of N random characters from that Alphabet
 */
val charGenerator: (Alphabet) -> (Int) -> List<Char> =
    { alphabet: Alphabet ->
        { count: Int -> (1..count).map { alphabet[Random.nextInt(0, alphabet.size)] } }
    }

val digits = charGenerator("0123456789".toCharArray())
val lowercase = charGenerator("abcdefghijklmnopqrstuvwxyz".toCharArray())
val uppercase = charGenerator("ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray())
val special = charGenerator("~@#$%^&*()!".toCharArray())


fun main() {
    // Generates a password comprised of:
    // 1 digit
    // 2 lowercase letters
    // 2 uppercase letters
    // 1 special character
    val password = (digits(1) + lowercase(2) + uppercase(2) + special(1))
        .shuffled()
        .joinToString(separator = "")

    println(password) // e.g. k)GuN6
}
