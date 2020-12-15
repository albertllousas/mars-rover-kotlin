package com.alo.marsrover.cli

import arrow.core.getOrDefault
import arrow.core.getOrElse
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.output.defaultCliktConsole
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.alo.marsrover.core.model.Direction.NORTH
import com.alo.marsrover.core.model.Grid
import com.alo.marsrover.core.model.Obstacle
import com.alo.marsrover.core.model.Point
import com.alo.marsrover.core.model.Position
import com.alo.marsrover.core.model.Rover

class MarsRover(
    private val roverCreator: (Position, Grid, (Obstacle) -> Unit) -> Rover = ::Rover,
    private val domainParser: DomainParser = DefaultParser,
    private val domainFormatter: DomainFormatter = DefaultFormatter,
    private val console: CliktConsole = defaultCliktConsole()
) : CliktCommand(
    help = """
    This script moves a rover around on a given grid.

    COMMANDS:
        - Move rover forward    -> f
        - Move rover backward   -> b
        - Turn the rover left   -> l
        - Turn the rover right  -> r
    """
) {

    companion object {
        const val pointFormatMessage = "format 'x y'"
    }

    val commands by commandsArgument()
    val grid by mapSizeOption()
    val initialPoint by initialPointOption()
    val direction by directionOption()
    val obstacles by obstaclesOption()

    override fun run() {
        roverCreator(
            Position(initialPoint, direction), grid.copy(obstacles = obstacles), this::report
        )
            .execute(commands = commands)
            .also { TermUi.echo(domainFormatter.formatResult(it), console = console) }
    }

    private fun report(obstacle: Obstacle) {
        TermUi.echo(domainFormatter.format(obstacle), console = console)
    }

    private fun commandsArgument() =
        argument(help = "array of commands")
            .convert { domainParser.parseCommands(it).getOrElse { error -> fail(error.message ?: "invalid commands") } }

    private fun mapSizeOption() =
        option("--map-size", help = "map size - format 'x y'")
            .convert {
                domainParser.parseGrid(it).getOrElse { fail(pointFormatMessage) }
            }.default(Grid(10, 10))

    private fun initialPointOption() =
        option(help = "initial rover starting point - format 'x y'")
            .convert {
                domainParser.parsePoint(it).getOrElse { fail(pointFormatMessage) }
            }.default(Point(0, 0))

    private fun directionOption() =
        option(help = "initial rover direction")
            .choice("N", "W", "S", "E")
            .convert {
                domainParser.parseDirection(it).getOrDefault { NORTH }
            }.default(NORTH)

    private fun obstaclesOption() =
        option("--obstacle", help = "initial starting point - format 'x y'")
            .convert {
                domainParser.parseObstacle(it).getOrElse { fail(pointFormatMessage) }
            }.multiple()

}
