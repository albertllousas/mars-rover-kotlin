package com.wallapop.marsrover.core.model

import arrow.core.Either

private val noop = { _: Any -> Unit }

data class Rover(
    val position: Position,
    val move: (Position, RoverCommand) -> Either<Obstacle, Position>,
    val report: (Obstacle) -> Unit = noop
) {
    fun execute(commands: List<RoverCommand>): Position {
        return position
    }

}
