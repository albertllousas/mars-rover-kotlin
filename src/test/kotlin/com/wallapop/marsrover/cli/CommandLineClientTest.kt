package com.wallapop.marsrover.cli

import com.github.ajalt.clikt.output.CliktConsole
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Test

internal class CommandLineClientTest {

    private val console = mock<CliktConsole>().also {
        given(it.lineSeparator).willReturn("\n")
    }

    @Test
    fun `command line client prints hello world`() {
        CommandLineClient(console).run()

        verify(console).print("Hello World!\n", false)
    }
}
