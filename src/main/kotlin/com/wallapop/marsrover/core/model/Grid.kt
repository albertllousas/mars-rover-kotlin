package com.wallapop.marsrover.core.model

import arrow.core.Either

data class Grid(val sizeX: Int, val sizeY: Int, val obstacles: List<Obstacle> = emptyList()) {
    fun tryToFit(position: Position): Either<Obstacle, Position> {
        val pointWithinBoundaries = Point(position.point.x % sizeX, position.point.y % sizeY)
        return obstacles
            .find { obstacle -> obstacle.point == pointWithinBoundaries }
            ?.let { Either.left(it) } ?: Either.right(position.copy(point = pointWithinBoundaries))
    }
}
