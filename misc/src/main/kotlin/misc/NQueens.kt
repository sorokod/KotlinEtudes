package misc

import kotlin.math.abs


const val SIZE: Int = 4

data class Queen(val col: Int, val row: Int)

/**
 * A convenience object for keeping track, adding and removing queens
 */
object Queens {
    private val queens = mutableListOf<Queen>()

    fun add(newQ: Queen): Boolean = valid(newQ) && queens.add(newQ)

    fun removeLast() = queens.removeLast()

    fun isSolved() = queens.size == SIZE

    fun print() {
        val board = IntArray(SIZE * SIZE)
        queens.forEach { board[it.row * SIZE + it.col] = 1 }

        board.forEachIndexed { i, k ->
            if (i.rem(SIZE) == 0) println()
            print("$k ")
        }
    }

    private fun valid(newQ: Queen) = queens.all { q -> valid(q, newQ) }
    private fun valid(q1: Queen, q2: Queen): Boolean =
        when {
            q1.row == q2.row || q1.col == q2.col || abs(q1.row - q2.row) == abs(q1.col - q2.col) -> false
            else -> true
        }
}

val queens = Queens

fun solveColumnsStartingFrom(col: Int): Boolean {
    if (queens.isSolved()) {
        return true
    }

    (0 until SIZE).forEach { row ->
        if (queens.add(Queen(col, row))) {
            if (solveColumnsStartingFrom(col + 1)) {
                return true
            } else {
                queens.removeLast()
            }
        }
    }
    return false
}

fun printSolution() {
    Queens.print()
}

fun main() {
    solveColumnsStartingFrom(0)
    printSolution()
}
