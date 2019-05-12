package com.wallapop.marsrover.core.model

import com.wallapop.marsrover.core.model.Direction.*

data class Point(val x: Int, val y: Int) {
    fun moveTo(direction: Direction) =
        when (direction) {
            NORTH -> this.copy(y = y.inc())
            SOUTH -> this.copy(y = y.dec())
            EAST -> this.copy(x = x.inc())
            WEST -> this.copy(x = x.dec())
        }
}
