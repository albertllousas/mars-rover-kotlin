package com.wallapop.marsrover.core.model

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right

private val noop = { _: Any -> Unit }

data class Rover(
    val position: Position,
    val move: (Position, RoverCommand) -> Either<Obstacle, Position>,
    val report: (Obstacle) -> Unit = noop
) {
    fun execute(commands: List<RoverCommand>): Position {
        return execute(position, commands)
    }

    private tailrec fun execute(currentPosition: Position, commands: List<RoverCommand>): Position =
        if (commands.isEmpty()) {
            currentPosition
        } else {
            when (val movement = move(currentPosition, commands.first())) {
                is Left -> currentPosition.also { report(movement.a) }
                is Right -> execute(movement.b, commands.drop(1))
            }
        }
}
