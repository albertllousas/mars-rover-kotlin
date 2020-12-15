package com.alo.marsrover

import com.github.ajalt.clikt.output.CliktConsole
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.alo.marsrover.cli.MarsRover
import org.junit.jupiter.api.Test

internal class MarsRoverAcceptanceTest {

    private val console = mock<CliktConsole>().also {
        given(it.lineSeparator).willReturn("\n")
    }

    private val cli = MarsRover(console = console)

    @Test
    fun `should move a rover around a grid`() {
        cli.main(arrayOf("ffrfflfbf","--initial-point=5 5","--direction=N", "--map-size=20 20"))

        verify(console).print("Rover position: '7 8' and direction: 'NORTH'\n", false)
    }

    @Test
    fun `should wrap from one edge of the grid to another`() {
        cli.main(arrayOf("fff","--initial-point=0 1","--direction=N", "--map-size=4 4"))

        verify(console).print("Rover position: '0 0' and direction: 'NORTH'\n", false)
    }

    @Test
    fun `should stop the rover when detects and obstacle and report it`() {
        cli.main(
            arrayOf("fffffff","--initial-point=0 1","--direction=N", "--map-size=4 4", "--obstacle=0 3")
        )

        verify(console).print("Obstacle detected at '0 3'\n", false)
        verify(console).print("Rover position: '0 2' and direction: 'NORTH'\n", false)
    }

}
