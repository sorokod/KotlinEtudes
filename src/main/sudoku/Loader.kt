package sudoku

import java.io.File

/**
 * Created by David Soroko on 19/11/2015.
 */


fun fromFile(filePath: String): Board {
    return fromList(File(filePath).readLines())
}

fun fromMultiFile(filePath: String): List<Board> {
    var key: String = ""
    fun keyFor(line: String): String {
        if (line.contains("Grid")) key = line
        return key
    }

    var boards: MutableList<Board> = arrayListOf()
    File(filePath)
            .readLines()
            .groupBy { keyFor(it) }
            .values.forEach { boards.add(fromList(it.drop(1))) }

    return boards
}


fun fromList(rows: List<String>): Board {
    val values = Array(rows.size, { IntArray(rows.size) })

    rows.forEachIndexed {
        rowNum, row ->
        row.forEachIndexed {
            i, char ->
            values[rowNum][i] = char.toInt() - '0'.toInt()
        }
    }
    return Board(values)
}


fun main(args: Array<String>) {
    val solver = Solver()

    val board = fromFile("resources/sudoku-difficult.txt")
    solver.solve(board)
    board.print(System.out)

    fromMultiFile("resources/p096_sudoku.txt").forEach {
        solver.solve(it)
        it.print(System.out)
    }

}
