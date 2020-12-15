package com.alo.marsrover.cli

import arrow.core.Try
import com.alo.marsrover.core.model.Direction
import com.alo.marsrover.core.model.Direction.EAST
import com.alo.marsrover.core.model.Direction.NORTH
import com.alo.marsrover.core.model.Direction.SOUTH
import com.alo.marsrover.core.model.Direction.WEST
import com.alo.marsrover.core.model.Grid
import com.alo.marsrover.core.model.MoveBackward
import com.alo.marsrover.core.model.MoveForward
import com.alo.marsrover.core.model.Obstacle
import com.alo.marsrover.core.model.Point
import com.alo.marsrover.core.model.RoverCommand
import com.alo.marsrover.core.model.TurnLeft
import com.alo.marsrover.core.model.TurnRight

interface DomainParser {
    fun parseDirection(direction: String): Try<Direction>
    fun parseGrid(grid: String): Try<Grid>
    fun parsePoint(point: String): Try<Point>
    fun parseObstacle(obstacle: String): Try<Obstacle>
    fun parseCommands(commands: String): Try<List<RoverCommand>>
}

object DefaultParser: DomainParser {

    private val pointRegex = "^(\\d+)\\s(\\d+)$".toRegex()

    override fun parseDirection(direction: String): Try<Direction> =
        when (direction) {
            "N" -> Try.just(NORTH)
            "S" -> Try.just(SOUTH)
            "W" -> Try.just(WEST)
            "E" -> Try.just(EAST)
            else -> Try.raiseError(IllegalArgumentException("'$direction' is not a valid direction"))
        }

    override fun parseGrid(grid: String): Try<Grid> =
        Try {
            val (sizeX, sizeY) = pointRegex.find(grid)!!.destructured
            Grid(sizeX = sizeX.toInt(), sizeY = sizeY.toInt())
        }

    override fun parsePoint(point: String): Try<Point> =
        Try {
            val (x, y) = pointRegex.find(point)!!.destructured
            Point(x = x.toInt(), y = y.toInt())
        }

    override fun parseObstacle(obstacle: String): Try<Obstacle> = parsePoint(obstacle).map(::Obstacle)

    override fun parseCommands(commands: String): Try<List<RoverCommand>> =
        commands
            .toCharArray()
            .map(this::parseCommand)
            .fold(initial = Try.just(emptyList())) { acc, current ->
                acc.flatMap { list -> current.map { element -> list.plus(element) } }
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
