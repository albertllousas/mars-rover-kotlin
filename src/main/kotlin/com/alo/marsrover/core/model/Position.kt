package com.alo.marsrover.core.model

data class Position(val point: Point, val direction: Direction) {
    fun calculateNext(command: RoverCommand): Position =
        when (command) {
            is MoveForward -> this.copy(point = point.moveTo(direction))
            is MoveBackward -> this.copy(point = point.moveTo(direction.opposite()))
            is TurnRight -> this.copy(direction = direction.right())
            is TurnLeft -> this.copy(direction = direction.left())
        }
}
