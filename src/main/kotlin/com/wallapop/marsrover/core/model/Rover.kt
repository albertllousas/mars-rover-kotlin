package com.wallapop.marsrover.core.model

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right


data class Rover(
    val position: Position,
    val move: (Position, RoverCommand) -> Either<Obstacle, Position>
) {
    fun execute(commands: List<RoverCommand>): Position {
        return execute(position, commands)
    }

    private tailrec fun execute(currentPosition: Position, commands: List<RoverCommand>): Position =
        if (commands.isEmpty()) {
            currentPosition
        } else {
            when (val movement = move(currentPosition, commands.first())) {
                is Left -> currentPosition
                is Right -> execute(movement.b, commands.drop(1))
            }
        }
}
