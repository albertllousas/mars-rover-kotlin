package com.alo.marsrover.cli

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.alo.marsrover.core.model.Direction.NORTH
import com.alo.marsrover.core.model.Grid
import com.alo.marsrover.core.model.Obstacle
import com.alo.marsrover.core.model.Point
import com.alo.marsrover.core.model.Position
import com.alo.marsrover.core.model.Rover
import org.junit.jupiter.api.Test

internal class DomainFormatterTest {

    @Test
    fun `should format an obstacle`() {
        assertThat(DefaultFormatter.format(Obstacle(Point(1, 1))))
            .isEqualTo("Obstacle detected at '1 1'")
    }

    @Test
    fun `should format the final result`() {
        val finalRover = Rover(Position(Point(1, 1), NORTH), Grid(10, 10))

        assertThat(DefaultFormatter.formatResult(finalRover))
            .isEqualTo("Rover position: '1 1' and direction: 'NORTH'")
    }
}
