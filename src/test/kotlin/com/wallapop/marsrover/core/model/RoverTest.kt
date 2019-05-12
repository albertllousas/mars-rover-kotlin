package com.wallapop.marsrover.core.model

import arrow.core.Either
import arrow.core.Right
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.wallapop.marsrover.core.model.Direction.EAST
import com.wallapop.marsrover.core.model.Direction.NORTH
import org.junit.jupiter.api.Test

internal class RoverTest {

    private val move = mock<(Position, RoverCommand) -> Either<Obstacle, Position>>()

    @Test
    fun `should not move rover when commands are empty`() {
        val initialPosition = Position(Point(1, 1), NORTH)
        val rover = Rover(initialPosition, move)

        val actual = rover.execute(emptyList())

        assertThat(actual).isEqualTo(initialPosition)
    }

    @Test
    fun `should move rover for and array of commands`() {
        val initialPosition = Position(Point(1, 1), NORTH)
        val secondPosition = Position(Point(1, 2), NORTH)
        val thirdPosition = Position(Point(1, 2), EAST)
        val finalPosition = Position(Point(2, 2), EAST)
        val rover = Rover(initialPosition, move)
        val commands = listOf(MoveForward, TurnRight, MoveForward)
        given(move.invoke(initialPosition, MoveForward)).willReturn(Right(secondPosition))
        given(move.invoke(secondPosition, TurnRight)).willReturn(Right(thirdPosition))
        given(move.invoke(thirdPosition, MoveForward)).willReturn(Right(finalPosition))

        val actual = rover.execute(commands)

        assertThat(actual).isEqualTo(finalPosition)
    }
}
