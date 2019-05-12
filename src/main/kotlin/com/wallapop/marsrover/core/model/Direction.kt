package com.wallapop.marsrover.core.model

enum class Direction {
    NORTH, EAST,SOUTH, WEST;

    fun left(): Direction = values()[(ordinal + 3) % 4]
    fun right(): Direction = values()[(ordinal + 1) % 4]
    fun opposite(): Direction = values()[(ordinal + 2) % 4]
}
