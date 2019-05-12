package com.wallapop.marsrover.core.model

data class Point(val x: Int, val y: Int) {
    fun moveTo(direction: Direction) =
        when (direction) {
            Direction.NORTH -> this.copy(y = y.inc())
            Direction.SOUTH -> this.copy(y = y.dec())
            Direction.EAST -> this.copy(x = x.inc())
            Direction.WEST -> this.copy(x = x.dec())
        }
}
