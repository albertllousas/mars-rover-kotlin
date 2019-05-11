package com.wallapop.marsrover.cli

import arrow.core.Try
import com.wallapop.marsrover.core.model.Direction
import com.wallapop.marsrover.core.model.Direction.*
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.Point

typealias DomainParser<T> = (String) -> Try<T>

object Parsers {

    private val pointRegex = "^(\\d+)\\s(\\d+)$".toRegex()

    fun parseDirection(direction: String): Try<Direction> =
        Try {
            when (direction) {
                "N" -> NORTH
                "S" -> SOUTH
                "W" -> WEST
                "E" -> EAST
                else -> throw IllegalArgumentException("'$direction' is not a valid direction")
            }
        }

    fun parseGrid(grid: String): Try<Grid> =
        Try {
            val (sizeX, sizeY) = pointRegex.find(grid)!!.destructured
            Grid(sizeX = sizeX.toInt(), sizeY = sizeY.toInt())
        }

    fun parsePoint(point: String): Try<Point> =
        Try {
            val (x, y) = pointRegex.find(point)!!.destructured
            Point(x = x.toInt(), y = y.toInt())
        }
}
