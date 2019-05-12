package com.wallapop.marsrover.core.usecase

import arrow.core.Either
import arrow.core.andThen
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.Obstacle
import com.wallapop.marsrover.core.model.Position
import com.wallapop.marsrover.core.model.Report
import com.wallapop.marsrover.core.model.Rover
import com.wallapop.marsrover.core.model.RoverCommand
import com.wallapop.marsrover.core.model.RoverMovement

class MoveRoverUseCase(
    val createRover: (Position, RoverMovement, Report) -> Rover =
        { position, move, report -> Rover(position, move, report) }
) {
    fun move(
        initialPosition: Position, grid: Grid, commands: List<RoverCommand>, report: (Obstacle) -> Unit
    ): Rover {
        val calculateNextPosition: (RoverCommand) -> Position = initialPosition::next
        val tryToFitInTheGrid: (Position) -> Either<Obstacle, Position> = grid::tryToFit
        val move: (RoverCommand) -> Either<Obstacle, Position> = calculateNextPosition.andThen(tryToFitInTheGrid)
        val rover = createRover(initialPosition, move, report)
        return rover.execute(commands)
    }
}
