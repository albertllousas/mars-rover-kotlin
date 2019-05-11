package com.wallapop.marsrover.cli

import arrow.core.Try
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.Point

typealias DomainParser<T> = (String) -> Try<T>

object Parsers {

    private val pointRegex = "^(\\d+)\\s(\\d+)$".toRegex()

    fun parseGrid(grid: String): Try<Grid> =
        Try {
            val (sizeX, sizeY) = pointRegex.find(grid)!!.destructured
            Grid(sizeX = sizeX.toInt(), sizeY = sizeY.toInt())
        }

    fun parsePoint(point: String) : Try<Point> =
        Try {
            val (x, y) = pointRegex.find(point)!!.destructured
            Point(x = x.toInt(), y = y.toInt())
        }
}
