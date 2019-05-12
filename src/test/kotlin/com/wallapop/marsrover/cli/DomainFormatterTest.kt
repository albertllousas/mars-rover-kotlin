package com.wallapop.marsrover.cli

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.wallapop.marsrover.core.model.Direction.NORTH
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.Obstacle
import com.wallapop.marsrover.core.model.Point
import com.wallapop.marsrover.core.model.Position
import com.wallapop.marsrover.core.model.Rover
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
