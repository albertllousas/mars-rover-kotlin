package com.alo.marsrover.core.model

sealed class RoverCommand {
    override fun toString(): String = this.javaClass.simpleName
}

object MoveForward : RoverCommand()
object MoveBackward : RoverCommand()
object TurnRight : RoverCommand()
object TurnLeft : RoverCommand()
