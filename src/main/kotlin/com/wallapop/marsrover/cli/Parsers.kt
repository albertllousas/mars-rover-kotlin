package com.wallapop.marsrover.cli

import arrow.core.Try
import com.wallapop.marsrover.core.model.Direction
import com.wallapop.marsrover.core.model.Direction.EAST
import com.wallapop.marsrover.core.model.Direction.NORTH
import com.wallapop.marsrover.core.model.Direction.SOUTH
import com.wallapop.marsrover.core.model.Direction.WEST
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.MoveBackward
import com.wallapop.marsrover.core.model.MoveForward
import com.wallapop.marsrover.core.model.Point
import com.wallapop.marsrover.core.model.RoverCommand
import com.wallapop.marsrover.core.model.TurnLeft
import com.wallapop.marsrover.core.model.TurnRight

typealias DomainParser<T> = (String) -> Try<T>

object Parsers {

    private val pointRegex = "^(\\d+)\\s(\\d+)$".toRegex()

    fun parseDirection(direction: String): Try<Direction> =
        when (direction) {
            "N" -> Try.just(NORTH)
            "S" -> Try.just(SOUTH)
            "W" -> Try.just(WEST)
            "E" -> Try.just(EAST)
            else -> Try.raiseError(IllegalArgumentException("'$direction' is not a valid direction"))
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

    fun parseCommand(command: Char): Try<RoverCommand> =
        when (command) {
            'f' -> Try.just(MoveForward)
            'b' -> Try.just(MoveBackward)
            'l' -> Try.just(TurnLeft)
            'r' -> Try.just(TurnRight)
            else -> Try.raiseError(IllegalArgumentException("Unknown command: '$command'"))
        }
}
