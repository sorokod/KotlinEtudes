package nqueens

import kotlin.math.abs


const val N: Int = 4

data class Queen(val col: Int, val row: Int = 0)

val queens = mutableListOf<Queen>()

fun valid(q: Queen, queens: List<Queen>) = queens.all { valid(it, q) }
fun valid(q1: Queen, q2: Queen): Boolean =
        when {
            q1.row == q2.row ||
            q1.col == q2.col ||
            abs(q1.row - q2.row) == abs(q1.col - q2.col) -> false
            else -> true
        }

fun printSolution() {
    val board = IntArray(N * N)
    queens.forEach { board[it.row * N + it.col] = 1 }

    board.forEachIndexed { i, k ->
        if (i.rem(N) == 0) println()
        print("$k ")
    }
}


fun solve(col: Int): Boolean {
    if (queens.size == N) {
        return true
    }

    (0 until N).forEach {
        val queen = Queen(col, it)

        if (valid(queen, queens)) {
            queens.add(queen)

            if (solve(col + 1)) {
                return true
            } else {
                queens.removeAt(queens.lastIndex)
            }
        }
    }
    return false
}

fun main() {
    solve(0)
    printSolution()
}