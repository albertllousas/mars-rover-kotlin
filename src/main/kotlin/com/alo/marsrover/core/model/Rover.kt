package com.alo.marsrover.core.model

import arrow.core.Either.Left
import arrow.core.Either.Right
import arrow.core.andThen

private val noop = { _: Any -> Unit }

typealias ReportObstacle = (Obstacle) -> Unit

data class Rover(
        val position: Position,
        val grid: Grid,
        private val report: ReportObstacle = noop
) {
    fun execute(commands: List<RoverCommand>): Rover = this.copy(position = execute(position, commands))

    private tailrec fun execute(currentPosition: Position, commands: List<RoverCommand>): Position =
            if (commands.isEmpty()) {
                currentPosition
            } else {
                val move = currentPosition::calculateNext.andThen(grid::tryToFit)
                when (val movement = move(commands.first())) {
                    is Left -> currentPosition.also { report(movement.a) }
                    is Right -> execute(movement.b, commands.drop(1))
                }
            }
}
