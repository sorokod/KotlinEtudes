package misc.sudoku


import java.io.PrintStream

data class Cell(val x: Int, val y: Int)

private val noCell = Cell(-1, -1)

class Board(private val values: Array<IntArray>, private val unit: Int = 3) {

    val dim = values.size
    private val indices = 0..dim - 1

    fun reset(cell: Cell) {
        values[cell.x][cell.y] = 0
    }

    fun isSolved() = nextFreeCell() == noCell

    fun nextFreeCell(): Cell {
        for (i in indices) {
            for (j in indices) {
                if (values[i][j] == 0) {
                    return Cell(i, j)
                }
            }
        }
        return noCell
    }

    fun place(cell: Cell, value: Int): Boolean {
        with(cell) {
            for (i in indices) {
                if (values[x][i] == value || values[i][y] == value) {
                    return false
                }
            }

            val xU = unit * (x / unit)
            val yU = unit * (y / unit)

            for (i in xU..xU + unit - 1)
                for (j in yU..yU + unit - 1)
                    if (values[i][j] == value)
                        return false

            values[x][y] = value
            return true

        }
    }


    fun print(ps: PrintStream) {
        for (i in indices) {
            if (i % unit == 0) {
                ps.println()
            }
            for (j in indices) {
                if (j % unit == 0) {
                    ps.print(' ')
                }
                ps.print(values[i][j])
            }
            ps.println()
        }
        ps.println("---------------")
    }
}
