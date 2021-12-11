package misc.sudoku


class Solver() {

    fun solve(board: Board): Boolean {

        if (board.isSolved()) return true

        val nextFreeCell = board.nextFreeCell()

        for (value in 1..board.dim)
            if (board.place(nextFreeCell, value))
                if (solve(board)) {
                    return true
                } else {
                    board.reset(nextFreeCell)
                }

        return false
    }
}


