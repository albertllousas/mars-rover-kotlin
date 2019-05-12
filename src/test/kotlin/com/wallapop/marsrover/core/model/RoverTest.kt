package com.wallapop.marsrover.core.model

import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.wallapop.marsrover.core.model.Direction.*
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
}
