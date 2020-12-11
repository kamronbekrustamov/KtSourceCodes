package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    object : Game {
        var currentCell = Cell(4,4)
        val board = createGameBoard<Int?>(4)
        override fun initialize() {
            for (i in 0..14) {
                board[Cell(i / 4 + 1, i % 4 + 1)] = initializer.initialPermutation[i]
            }
        }

        override fun canMove(): Boolean {
            return true
        }

        override fun hasWon(): Boolean {
            for (i in 0..14) {
                if (board[Cell(i / 4 + 1, i % 4 + 1)] != (i + 1))
                    return false
            }
            return true
        }

        override fun processMove(direction: Direction) {
            val cell = currentCell.getNeighbour(direction)
            if(cell != null) {
                board[currentCell] = board[cell]
                board[cell] = null
                currentCell = cell
            }
        }

        fun Cell.getNeighbour(direction: Direction): Cell? {
            val cells = board.getAllCells().toMutableList()
            val width = board.width
            val index = cells.indexOf(this)
            return when(direction) {
                Direction.DOWN -> if (index - width < 0) null else cells[index - width]
                Direction.UP -> if (index + width >= cells.size) null else cells[index + width]
                Direction.RIGHT -> if (index - 1 < 0) null else cells[index - 1]
                Direction.LEFT -> if (index + 1 >= cells.size) null else cells[index + 1]
            }
        }

        override fun get(i: Int, j: Int): Int? {
            return board[Cell(i, j)]
        }

    }

