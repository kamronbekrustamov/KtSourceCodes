package board

import board.Direction.*
import java.lang.IllegalArgumentException

fun createSquareBoard(width: Int): SquareBoard = object: SquareBoard {
    override val width: Int = width
    val cells = mutableListOf<Cell>()

    init {
        for (i in 1..width)
            for (j in 1..width)
                this.cells.add(Cell(i, j))
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if ((i !in 1..width) || (j !in 1..width))
            return null
        return this.cells[(i - 1) * width - 1 + j]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if ((i !in 1..width) || (j !in 1..width))
            throw IllegalArgumentException()
        return this.cells[(i - 1) * width - 1 + j]
    }

    override fun getAllCells(): Collection<Cell> {
        return this.cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list = mutableListOf<Cell>()
        val range = if (width >= jRange.last) jRange else jRange.first..width
        for (j in range)
            list.add(cells[(i - 1) * width - 1 + j])
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list = mutableListOf<Cell>()
        val range = if (width >= iRange.last) iRange else iRange.first..width
        for (i in range)
            list.add(cells[(i - 1) * width - 1 + j])
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val index = cells.indexOf(this)
        return when(direction) {
            UP -> if (index - width < 0) null else cells[index - width]
            DOWN -> if (index + width > cells.size) null else cells[index + width]
            LEFT -> if (index - 1 < 0) null else cells[index - 1]
            RIGHT -> if (index + 1 >= cells.size) null else cells[index + 1]
        }
    }
}
fun <T> createGameBoard(width: Int): GameBoard<T> = object : GameBoard<T> {
    override val width: Int = width
    val cells = mutableListOf<Cell>()
    val mapWithCell = mutableMapOf<Cell, T?>()

    init {
        for (i in 1..width)
            for (j in 1..width)
                this.cells.add(Cell(i, j))
        for (cell in cells)
            mapWithCell[cell] = null
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if ((i !in 1..width) || (j !in 1..width))
            return null
        return this.cells[(i - 1) * width - 1 + j]
    }

    override fun getCell(i: Int, j: Int): Cell {
        if ((i !in 1..width) || (j !in 1..width))
            throw IllegalArgumentException()
        return this.cells[(i - 1) * width - 1 + j]
    }

    override fun getAllCells(): Collection<Cell> {
        return this.cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list = mutableListOf<Cell>()
        val range = if (width >= jRange.last) jRange else jRange.first..width
        for (j in range)
            list.add(cells[(i - 1) * width - 1 + j])
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list = mutableListOf<Cell>()
        val range = if (width >= iRange.last) iRange else iRange.first..width
        for (i in range)
            list.add(cells[(i - 1) * width - 1 + j])
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val index = cells.indexOf(this)
        return when(direction) {
            UP -> if (index - width < 0) null else cells[index - width]
            DOWN -> if (index + width > cells.size) null else cells[index + width]
            LEFT -> if (index - 1 < 0) null else cells[index - 1]
            RIGHT -> if (index + 1 >= cells.size) null else cells[index + 1]
        }
    }

    override fun get(cell: Cell): T? {
        return mapWithCell[cell]
    }

    override fun set(cell: Cell, value: T?) {
        mapWithCell[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        val list = mutableListOf<Cell>()
        for ((key, value) in mapWithCell)
            if (predicate.invoke(value))
                list.add(key)
        return list
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        for ((key, value) in mapWithCell)
            if (predicate.invoke(value))
                return key
        return null
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        for (item in mapWithCell)
            if (predicate.invoke(item.value))
                return true
        return false
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        for (item in mapWithCell)
            if (!predicate.invoke(item.value))
                return false
        return true
    }

}

