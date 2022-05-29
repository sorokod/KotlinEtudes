package misc.sudoku

import misc.asResourceFile
import java.io.File

fun fromResourceFile(path: String): Board {
    return fromList(path.asResourceFile().readLines())
}

fun fromFile(path: String): Board {
    return fromList(File(path).readLines())
}

fun fromMultiFile(path: String): List<Board> {
    var key: String = ""
    fun keyFor(line: String): String {
        if (line.contains("Grid")) key = line
        return key
    }

    var boards: MutableList<Board> = arrayListOf()
    path.asResourceFile()
        .readLines()
        .groupBy { keyFor(it) }
        .values.forEach { boards.add(fromList(it.drop(1))) }

    return boards
}


fun fromList(rows: List<String>): Board {
    val values = Array(rows.size, { IntArray(rows.size) })

    rows.forEachIndexed { rowNum, row ->
        row.forEachIndexed { i, char ->
            values[rowNum][i] = char.code - '0'.code
        }
    }
    return Board(values)
}


fun main() {
    val solver = Solver()

    val board = fromResourceFile("/sudoku-difficult.txt")
    solver.solve(board)
    board.print(System.out)

    fromMultiFile("/p096_sudoku.txt").forEach {
        solver.solve(it)
        it.print(System.out)
    }

}
