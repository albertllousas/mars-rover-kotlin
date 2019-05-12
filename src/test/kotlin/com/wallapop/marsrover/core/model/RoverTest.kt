package com.wallapop.marsrover.core.model

import arrow.core.Left
import arrow.core.Right
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.wallapop.marsrover.core.model.Direction.NORTH
import org.junit.jupiter.api.Test

internal class RoverTest {


    private val grid = mock<Grid>()


    @Test
    fun `should not move rover when commands are empty`() {
        val initialPosition = Position(Point(1, 1), NORTH)
        val rover = Rover(initialPosition, grid)

        val actual = rover.execute(emptyList())

        assertThat(actual).isEqualTo(rover.copy(initialPosition))
    }

    @Test
    fun `should move rover for and array of commands`() {
        val initialPosition = mock<Position>()
        val secondPosition = mock<Position>()
        val finalPosition = mock<Position>()
        val rover = Rover(initialPosition, grid)
        val commands = listOf(MoveForward, TurnRight)
        given(initialPosition.calculateNext(MoveForward)).willReturn(secondPosition)
        given(grid.tryToFit(secondPosition)).willReturn(Right(secondPosition))
        given(secondPosition.calculateNext(TurnRight)).willReturn(finalPosition)
        given(grid.tryToFit(finalPosition)).willReturn(Right(finalPosition))

        val actual = rover.execute(commands)

        assertThat(actual).isEqualTo(rover.copy(finalPosition))
    }

    @Test
    fun `should report rover when an obstacle is detected and stay in the last position`() {
        val initialPosition = mock<Position>()
        val secondPosition = mock<Position>()
        val obstacle = Obstacle(Point(1, 2))
        val report = mock<(Obstacle) -> Unit>()
        val rover = Rover(initialPosition, grid, report = report)
        val commands = listOf(MoveForward, TurnRight, MoveForward)
        given(initialPosition.calculateNext(MoveForward)).willReturn(secondPosition)
        given(grid.tryToFit(secondPosition)).willReturn(Left(obstacle))

        val actual = rover.execute(commands)

        assertThat(actual).isEqualTo(rover.copy(initialPosition))

        verify(report).invoke(obstacle)
    }
}
