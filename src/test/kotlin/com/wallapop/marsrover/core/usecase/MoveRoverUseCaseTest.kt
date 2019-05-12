package com.wallapop.marsrover.core.usecase

import arrow.core.andThen
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.wallapop.marsrover.core.model.Direction
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.MoveForward
import com.wallapop.marsrover.core.model.Obstacle
import com.wallapop.marsrover.core.model.Point
import com.wallapop.marsrover.core.model.Position
import com.wallapop.marsrover.core.model.Report
import com.wallapop.marsrover.core.model.Rover
import com.wallapop.marsrover.core.model.RoverMovement
import org.junit.jupiter.api.Test

internal class MoveRoverUseCaseTest {

    private val createRover = mock<(Position, RoverMovement, Report) -> Rover>()

    private val rover = mock<Rover>()

    private val report = mock<(Obstacle) -> Unit>()

    private val moveRoverUseCase = MoveRoverUseCase(createRover)

    @Test
    fun `should orchestrate and execute commands on the rover`() {
        val initialPosition = Position(Point(0, 0), Direction.NORTH)
        val grid = Grid(2, 2)
        val commands = listOf(MoveForward)
        val moveFn = initialPosition::next.andThen(grid::tryToFit)
        val finalRover = Rover(Position(Point(0, 1), Direction.NORTH), moveFn)

        given(createRover.invoke(eq(initialPosition), any(), eq(report))).willReturn(rover)
        given(rover.execute(commands)).willReturn(finalRover)

        val result = moveRoverUseCase.move(initialPosition, grid, commands, report)

        assertThat(result).isEqualTo(finalRover)
    }
}
