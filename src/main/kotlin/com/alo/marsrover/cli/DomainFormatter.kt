package com.alo.marsrover.cli

import com.alo.marsrover.core.model.Obstacle
import com.alo.marsrover.core.model.Point
import com.alo.marsrover.core.model.Rover

interface DomainFormatter {
    fun format(obstacle: Obstacle): String
    fun formatResult(rover: Rover): String
}

object DefaultFormatter : DomainFormatter {

    override fun format(obstacle: Obstacle) =
        "Obstacle detected at '${formatPoint(obstacle.point)}'"

    override fun formatResult(rover: Rover) =
        "Rover position: '${formatPoint(rover.position.point)}' and direction: '${rover.position.direction}'"

    private fun formatPoint(point: Point) = "${point.x} ${point.y}"
}
