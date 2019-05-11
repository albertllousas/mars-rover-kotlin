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
import com.wallapop.marsrover.core.model.Point
import org.junit.jupiter.api.Test

internal class CommandLineClientTest {

    private val console = mock<CliktConsole>().also {
        given(it.lineSeparator).willReturn("\n")
    }

    private val gridParser = mock<DomainParser<Grid>>()

    private val pointParser = mock<DomainParser<Point>>()

    private val commandLineClient = CommandLineClient(console = console)

    @Test
    fun `command line client accepts map size option`() {
        given(gridParser.invoke(any())).willReturn(Try.just(Grid(1, 2)))

        commandLineClient.main(arrayOf("--map-size=1 2"))

        assertThat(commandLineClient.grid).isEqualTo(Grid(1, 2))
    }

    @Test
    fun `command line client accepts multiple obstacles options`() {
        given(pointParser.invoke(any()))
            .willReturn(Try.just(Point(1, 2)))
            .willReturn(Try.just(Point(2, 3)))

        commandLineClient.main(arrayOf("--obstacle=1 2", "--obstacle=2 3"))

        assertThat(commandLineClient.obstacles).isEqualTo(listOf(Point(1, 2), Point(2, 3)))
    }

    @Test
    fun `command line client accepts initial rover starting option`() {
        given(pointParser.invoke(any())).willReturn(Try.just(Point(0, 0)))

        commandLineClient.main(arrayOf("--initial-point=0 0"))

        assertThat(commandLineClient.initialPoint).isEqualTo(Point(0, 0))
    }

    @Test
    fun `command line client accepts initial rover direction`() {
        given(pointParser.invoke(any())).willReturn(Try.just(Point(0, 0)))

        commandLineClient.main(arrayOf("--direction=N"))

        assertThat(commandLineClient.direction).isEqualTo(NORTH)
    }

    @Test
    fun `command line client prints hello world`() {
        given(gridParser.invoke(any())).willReturn(Try.just(Grid(1, 2)))

        commandLineClient.main(emptyArray())

        assertAll {
            assertThat(commandLineClient.initialPoint).isEqualTo(Point(0, 0))
            assertThat(commandLineClient.grid).isEqualTo(Grid(10, 10))
            assertThat(commandLineClient.obstacles).isEqualTo(emptyList<Point>())
            assertThat(commandLineClient.direction).isEqualTo(NORTH)
        }
        verify(console).print("Hello World!\n", false)
    }


}
