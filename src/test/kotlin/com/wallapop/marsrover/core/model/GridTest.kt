package com.wallapop.marsrover.core.model

import arrow.core.Either
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.wallapop.marsrover.core.model.Direction.NORTH
import org.junit.jupiter.api.Test

internal class GridTest {

    private val grid = Grid(3, 3, obstacles = listOf(Obstacle(Point(2, 2))))

    @Test
    fun `should fit a position inside the grid`() {
        val position = Position(Point(1, 1), NORTH)
        assertThat(grid.tryToFit(position)).isEqualTo(Either.right(position))
    }

    @Test
    fun `should fit a position outside the grid with an analogous within the boundaries`() {
        val position = Position(Point(4, 1), NORTH)
        assertThat(grid.tryToFit(position)).isEqualTo(Either.right(position.copy(point = Point(1, 1))))
    }

    @Test
    fun `should fail fitting a position inside the grid when there is an obstacle`() {
        val position = Position(Point(2, 2), NORTH)
        assertThat(grid.tryToFit(position)).isEqualTo(Either.left(Obstacle(Point(2, 2))))
    }

    @Test
    fun `should fail fitting a position outside the grid when there is an obstacle in the analogous position`() {
        val position = Position(Point(5, 5), NORTH)
        assertThat(grid.tryToFit(position)).isEqualTo(Either.left(Obstacle(Point(2, 2))))
    }

}
