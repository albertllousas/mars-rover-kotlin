package com.wallapop.marsrover.cli

import arrow.core.getOrDefault
import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.defaultCliktConsole
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.wallapop.marsrover.core.model.Direction
import com.wallapop.marsrover.core.model.Direction.*
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.Point

class CommandLineClient(
    private val parseGrid: DomainParser<Grid> = Parsers::parseGrid,
    private val parsePoint: DomainParser<Point> = Parsers::parsePoint,
    private val parseDirection: DomainParser<Direction> = Parsers::parseDirection,
    private val console: CliktConsole = defaultCliktConsole()
) : CliktCommand() {

    companion object {
        const val pointFormatMessage = "format 'x y'"
    }

    val grid by option("--map-size", help = "map size - format 'x y'")
        .convert {
            parseGrid(it).getOrElse { fail(pointFormatMessage) }
        }.default(Grid(10, 10))

    val initialPoint by option(help = "initial rover starting point - format 'x y'")
        .convert {
            parsePoint(it).getOrElse { fail(pointFormatMessage) }
        }.default(Point(0, 0))

    val direction by option(help = "initial rover direction - ")
        .choice("N", "W", "S", "E")
        .convert {
            parseDirection(it).getOrDefault { NORTH }
        }.default(NORTH )


    val obstacles by option("--obstacle", help = "initial starting point - format 'x y'")
        .convert {
            parsePoint(it).getOrElse { fail(pointFormatMessage) }
        }.multiple()

    override fun run() {
        TermUi.echo("Hello World!", console = console)
    }
}
