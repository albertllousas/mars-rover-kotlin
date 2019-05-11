package com.wallapop.marsrover.cli

import arrow.core.Try
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.output.CliktConsole
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.wallapop.marsrover.core.model.Direction.NORTH
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.MoveBackward
import com.wallapop.marsrover.core.model.MoveForward
import com.wallapop.marsrover.core.model.Point
import com.wallapop.marsrover.core.model.RoverCommand
import com.wallapop.marsrover.core.model.TurnRight
import org.junit.jupiter.api.Test

internal class MarsRoverTest {

    private val console = mock<CliktConsole>().also {
        given(it.lineSeparator).willReturn("\n")
    }

    private val commandsParser = mock<DomainParser<List<RoverCommand>>>()

    private val gridParser = mock<DomainParser<Grid>>()

    private val pointParser = mock<DomainParser<Point>>()

    private val commandLineClient = MarsRover(console = console)

    private val commands = "fbr"

    @Test
    fun `rover receives a character array of commands`() {
        val domainCommands = listOf(MoveForward, MoveBackward, TurnRight)
        given(commandsParser.invoke(any())).willReturn(Try.just(domainCommands))

        commandLineClient.main(arrayOf(commands))

        assertThat(commandLineClient.commands).isEqualTo(domainCommands)
    }

    @Test
    fun `rover accepts map size option`() {
        given(gridParser.invoke(any())).willReturn(Try.just(Grid(1, 2)))

        commandLineClient.main(arrayOf(commands, "--map-size=1 2"))

        assertThat(commandLineClient.grid).isEqualTo(Grid(1, 2))
    }

    @Test
    fun `rover accepts multiple obstacles options`() {
        given(pointParser.invoke(any()))
            .willReturn(Try.just(Point(1, 2)))
            .willReturn(Try.just(Point(2, 3)))

        commandLineClient.main(arrayOf(commands, "--obstacle=1 2", "--obstacle=2 3"))

        assertThat(commandLineClient.obstacles).isEqualTo(listOf(Point(1, 2), Point(2, 3)))
    }

    @Test
    fun `rover accepts initial rover starting option`() {
        given(pointParser.invoke(any())).willReturn(Try.just(Point(0, 0)))

        commandLineClient.main(arrayOf(commands, "--initial-point=0 0"))

        assertThat(commandLineClient.initialPoint).isEqualTo(Point(0, 0))
    }

    @Test
    fun `rover accepts initial rover direction`() {
        given(pointParser.invoke(any())).willReturn(Try.just(Point(0, 0)))

        commandLineClient.main(arrayOf(commands, "--direction=N"))

        assertThat(commandLineClient.direction).isEqualTo(NORTH)
    }

    @Test
    fun `rover prints hello world`() {
        given(gridParser.invoke(any())).willReturn(Try.just(Grid(1, 2)))

        commandLineClient.main(arrayOf(commands))

        assertAll {
            assertThat(commandLineClient.initialPoint).isEqualTo(Point(0, 0))
            assertThat(commandLineClient.grid).isEqualTo(Grid(10, 10))
            assertThat(commandLineClient.obstacles).isEqualTo(emptyList<Point>())
            assertThat(commandLineClient.direction).isEqualTo(NORTH)
        }
        verify(console).print("Hello World!\n", false)
    }


}
