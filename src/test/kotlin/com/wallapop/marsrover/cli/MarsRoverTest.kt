package com.wallapop.marsrover.cli

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.output.CliktConsole
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.wallapop.marsrover.core.model.Direction
import com.wallapop.marsrover.core.model.Direction.NORTH
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.MoveBackward
import com.wallapop.marsrover.core.model.MoveForward
import com.wallapop.marsrover.core.model.Obstacle
import com.wallapop.marsrover.core.model.Point
import com.wallapop.marsrover.core.model.Position
import com.wallapop.marsrover.core.model.Rover
import com.wallapop.marsrover.core.model.TurnRight
import org.junit.jupiter.api.Test

internal class MarsRoverTest {

    private val roverCreator = mock<(Position, Grid, (Obstacle) -> Unit) -> Rover>()

    private val console = mock<CliktConsole>().also {
        given(it.lineSeparator).willReturn("\n")
    }

    private val commandLineClient = MarsRover(console = console)

    private val commands = "fbr"

    @Test
    fun `rover receives a character array of commands`() {
        val domainCommands = listOf(MoveForward, MoveBackward, TurnRight)

        commandLineClient.main(arrayOf(commands))

        assertThat(commandLineClient.commands).isEqualTo(domainCommands)
    }

    @Test
    fun `rover accepts map size option`() {
        commandLineClient.main(arrayOf(commands, "--map-size=1 2"))

        assertThat(commandLineClient.grid).isEqualTo(Grid(1, 2))
    }

    @Test
    fun `rover accepts multiple obstacles options`() {
        commandLineClient.main(arrayOf(commands, "--obstacle=1 2", "--obstacle=2 3"))

        assertThat(commandLineClient.obstacles)
            .isEqualTo(listOf(Obstacle(Point(1, 2)), Obstacle(Point(2, 3))))
    }

    @Test
    fun `rover accepts initial rover starting option`() {
        commandLineClient.main(arrayOf(commands, "--initial-point=0 0"))

        assertThat(commandLineClient.initialPoint).isEqualTo(Point(0, 0))
    }

    @Test
    fun `rover accepts initial rover direction`() {
        commandLineClient.main(arrayOf(commands, "--direction=S"))

        assertThat(commandLineClient.direction).isEqualTo(Direction.SOUTH)
    }

    @Test
    fun `rover executes an array of commands and print it to the console`() {
        val finalRover = Rover(Position(Point(1, 1), NORTH), Grid(10, 10))
        given(roverCreator.invoke(any(), any(), any())).willReturn(finalRover)
        commandLineClient.main(arrayOf(commands))

        verify(console).print("Rover position: '0 0' and direction: 'EAST'\n", false)
    }

    @Test
    fun `rover prints a message when an obstacle is detected`() {
        commandLineClient.main(arrayOf(commands))

        verify(console).print("Rover position: '0 0' and direction: 'EAST'\n", false)
    }

    @Test
    fun `rover initializes all the defaults when no options are provoded`() {
        commandLineClient.main(arrayOf(commands))

        assertAll {
            assertThat(commandLineClient.initialPoint).isEqualTo(Point(0, 0))
            assertThat(commandLineClient.grid).isEqualTo(Grid(10, 10))
            assertThat(commandLineClient.obstacles).isEqualTo(emptyList<Point>())
            assertThat(commandLineClient.direction).isEqualTo(NORTH)
        }
    }


}
