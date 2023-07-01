package rosettacode

// https://rosettacode.org/wiki/Bifid_cipher
//
// ABCDEFGHIKLMNOPQRSTUVWXYZ
//
// ATTACKATDAWN
// DQBDAXDQPDQH

const val square = "ABCDEFGHIKLMNOPQRSTUVWXYZ"

object Square {
    private const val square: String = "ABCDEFGHIKLMNOPQRSTUVWXYZ"
    private fun encode(ch: Char): Pair<Int, Int> = square.indexOf(ch).let { idx -> Pair(idx / 5, idx.mod(5)) }
    fun encode(data: String): List<Pair<Int, Int>> = data.map { char -> encode(char) }
    fun decode(pair: Pair<Int, Int>): Char = square[pair.first * 5 + pair.second]
    fun decode(pair: List<Int>): Char = square[pair[0] * 5 + pair[1]]
}

fun enc(data: String): String {
    val listOfPairs = Square.encode(data).also { println("P1: $it") } // AT => [(0,0) , (3,3)


    val pairOfLists = listOfPairs.fold(Pair(listOf<Int>(), listOf<Int>())) { acc, pair ->
        Pair(acc.first + pair.first, acc.second + pair.second)
    }.also { println("P2: $it") } // => ([0, 3..], [0, 3...)

    val part3 = pairOfLists.first + pairOfLists.second
    println("P3: $part3") // [0, 3, ... 0, 3 ...]

    return part3.windowed(size = 2, step = 2)
        .map { Square.decode(it) }
        .let { String(it.toCharArray()) }
}

fun dec(data: String): String {
    val d1 = Square.encode(data)
        .fold(listOf<Int>()) { acc, pair -> acc + pair.first + pair.second }
        .also { println("D1: $it") }

    val d2 = d1.chunked(data.length).also { println("D2: $it") }
    val d3 = d2[0].zip(d2[1]).also { println("D3: $it") }
    val d4 = d3.map { Square.decode(it) }.also { println("D4: $it") }
    return String(d4.toCharArray())
}


fun main() {

    enc("ATTACKATDAWN").also { println("> $it") }
    dec("DQBDAXDQPDQH").also { println("> $it") }
    check(dec("DQBDAXDQPDQH") == "ATTACKATDAWN")

    enc("A").also { println("> $it") }
    dec("A").also { println("> $it") }

    enc("AT").also { println("> $it") }
    dec("DD").also { println("> $it") }

//
//    val part1 = "ATTACKATDAWN".map { char -> Square.encode(char) }
//        .also { println(it) }
//
//    val part3 = part1.fold(Pair(listOf<Int>(), listOf<Int>())) { acc, pair ->
//        Pair(acc.first + pair.first, acc.second + pair.second)
//    }.also { println("part3 $it") }
//
//    val part4 = part3.first + part3.second
//    println("part4 $part4")
//
//
//
//    part4.windowed(size = 2, step = 2)
//        .map { Square.decode(it) }
//        .joinToString(separator = "")
//        .also { println(it) }
}