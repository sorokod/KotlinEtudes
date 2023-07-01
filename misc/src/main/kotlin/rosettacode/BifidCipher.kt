package rosettacode

import kotlin.math.sqrt

// https://rosettacode.org/wiki/Bifid_cipher
//

data class Square(private val square: String) {
    private val dim: Int = sqrt(square.length.toDouble()).toInt()
    fun encode(ch: Char): Pair<Int, Int> = square.indexOf(ch).let { idx -> Pair(idx / dim, idx.mod(dim)) }
    fun decode(pair: List<Int>): Char = square[pair[0] * dim + pair[1]]
    fun decode(row: Int, col: Int): Char = square[row * dim + col]
}


class Bifid(private val sq: Square) {
    fun encrypt(str: String): String {
        fun expandAndScatter(str: String): IntArray {
            val buffer = IntArray(str.length * 2)
            str.forEachIndexed { i, ch ->
                val (row, col) = sq.encode(ch)
                buffer[i] = row
                buffer[str.length + i] = col
            }
            return buffer
        }


        val buffer = expandAndScatter(str)

        val characters: List<Char> = buffer.asIterable()
            .windowed(size = 2, step = 2)
            .map { sq.decode(it) }

        return String(characters.toCharArray())
    }

    fun decrypt(str: String): String {
        fun expand(str: String): IntArray {
            val buffer = IntArray(str.length * 2)
            for (i in buffer.indices step 2) {
                val (row, col) = sq.encode(str[i / 2])
                buffer[i] = row
                buffer[1 + i] = col
            }
            return buffer
        }

        val buffer = expand(str)

        val characters = str.toCharArray()
        for (i in characters.indices) {
            characters[i] = sq.decode(buffer[i], buffer[characters.size + i])
        }
        return String(characters)
    }
}


fun main() {
    val bifidABC_5x5 = Bifid(Square("ABCDEFGHIKLMNOPQRSTUVWXYZ"))
    val bifidABC_6x6 = Bifid(Square("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"))
    val bifidBGW_5x5 = Bifid(Square("BGWKZQPNDSIOAXEFCLUMTHYVR"))

    bifidABC_5x5.encrypt("ATTACKATDAWN").also { println("$it") }
    bifidABC_5x5.decrypt("DQBDAXDQPDQH").also { println("$it") }

    bifidBGW_5x5.encrypt("FLEEATONCE").also { println("$it") }
    bifidBGW_5x5.decrypt("UAEOLWRINS").also { println("$it") }

    bifidBGW_5x5.encrypt("ATTACKATDAWN").also { println("$it") }
    bifidBGW_5x5.decrypt("EYFENGIWDILA").also { println("$it") }

    bifidABC_6x6.encrypt("THEINVASIONWILLSTARTONTHEFIRSTOFJANUARY").also { println("$it") }
    bifidABC_6x6.decrypt("TBPDIPHJSPOTAIVMGPCZKNSCN09BFIHK64I7BM4").also { println("$it") }
}