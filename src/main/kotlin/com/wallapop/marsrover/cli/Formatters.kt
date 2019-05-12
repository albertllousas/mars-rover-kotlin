package com.wallapop.marsrover.cli

import com.wallapop.marsrover.core.model.Obstacle
import com.wallapop.marsrover.core.model.Point
import com.wallapop.marsrover.core.model.Rover

interface DomainFormatter {
    fun formatObstacle(obstacle: Obstacle): String
    fun formatResult(rover: Rover): String
}

object DefaultFormatter : DomainFormatter {

    override fun formatObstacle(obstacle: Obstacle) =
        "Obstacle detected at '${formatPoint(obstacle.point)}'"

    override fun formatResult(rover: Rover) =
        "Rover position: '${formatPoint(rover.position.point)}' and direction: '${rover.position.direction}'"

    private fun formatPoint(point: Point) = "${point.x} ${point.y}"
}
