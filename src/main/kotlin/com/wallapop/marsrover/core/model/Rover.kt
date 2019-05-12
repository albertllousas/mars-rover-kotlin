package com.wallapop.marsrover.core.model

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right

private val noop = { _: Any -> Unit }

typealias RoverMovement = (command: RoverCommand) -> Either<Obstacle, Position>

typealias Report = (Obstacle) -> Unit

data class Rover(
    val position: Position,
    private val move: RoverMovement,
    private val report: Report = noop
) {

    fun execute(commands: List<RoverCommand>): Rover = this.copy(position = execute(position, commands))

    private tailrec fun execute(currentPosition: Position, commands: List<RoverCommand>): Position =
        if (commands.isEmpty()) {
            currentPosition
        } else {
            when (val movement = move(commands.first())) {
                is Left -> currentPosition.also { report(movement.a) }
                is Right -> execute(movement.b, commands.drop(1))
            }
        }
}
