package com.wallapop.marsrover.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.defaultCliktConsole

class CommandLineClient(private val console: CliktConsole = defaultCliktConsole()) : CliktCommand() {

    override fun run() {
        TermUi.echo("Hello World!", console = console)
    }
}
