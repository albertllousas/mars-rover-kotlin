package com.wallapop.marsrover.cli

import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.defaultCliktConsole
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.wallapop.marsrover.core.model.Grid

class CommandLineClient(
    private val parseGrid: DomainParser<Grid> = Parsers::parseGrid,
    private val console: CliktConsole = defaultCliktConsole()
) : CliktCommand() {

    val grid by option("--map-size", help = "map size - format 'x y'")
        .convert {
            parseGrid(it).getOrElse { fail("format 'x y'") }
        }.prompt("Insert map size (x,y)")

    override fun run() {
        TermUi.echo("Hello World!", console = console)
    }
}
