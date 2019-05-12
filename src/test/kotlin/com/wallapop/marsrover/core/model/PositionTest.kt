package com.wallapop.marsrover.core.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.wallapop.marsrover.core.model.Direction.*
import org.junit.Test

internal class PositionTest {
    private val position = Position(Point(2, 2), NORTH)

    @Test
    fun `should calculate the next position for a move forward command`() {
        assertThat(position.calculateNext(MoveForward)).isEqualTo(Position(Point(2, 3), NORTH))
    }

    @Test
    fun `should calculate the next position for a move backward command`() {
        assertThat(position.calculateNext(MoveBackward)).isEqualTo(Position(Point(2, 1), NORTH))
    }

    @Test
    fun `should calculate the next position for a turn right command`() {
        assertThat(position.calculateNext(TurnRight)).isEqualTo(Position(Point(2, 2), EAST))
    }

    @Test
    fun `should calculate the next position for a turn left command`() {
        assertThat(position.calculateNext(TurnLeft)).isEqualTo(Position(Point(2, 2), WEST))
    }
}
