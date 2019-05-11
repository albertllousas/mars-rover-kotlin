package com.wallapop.marsrover.cli

import arrow.core.Try
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.ajalt.clikt.output.CliktConsole
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
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

    private val arguments = arrayOf("--map-size=1 2", "--initial-point=0 0")

    @Test
    fun `command line client accepts map size option`() {

        given(gridParser.invoke(any())).willReturn(Try.just(Grid(1, 2)))

        commandLineClient.main(arguments)

        assertThat(commandLineClient.grid).isEqualTo(Grid(1, 2))

    }

    @Test
    fun `command line client accepts initial starting option`() {

        given(pointParser.invoke(any())).willReturn(Try.just(Point(1, 2)))

        commandLineClient.main(arguments)

        assertThat(commandLineClient.grid).isEqualTo(Grid(1, 2))

    }

    @Test
    fun `command line client prints hello world`() {

        given(gridParser.invoke(any())).willReturn(Try.just(Grid(1, 2)))

        commandLineClient.main(arguments)

        verify(console).print("Hello World!\n", false)
    }


}
