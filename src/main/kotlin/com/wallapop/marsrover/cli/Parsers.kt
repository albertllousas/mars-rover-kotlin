package com.wallapop.marsrover.cli

import arrow.core.Try
import com.wallapop.marsrover.core.model.Grid

typealias DomainParser<T> = (String) -> Try<T>

object Parsers {

    private val gridSizeRegex = "^(\\d+)\\s(\\d+)$".toRegex()

    fun parseGrid(grid: String) : Try<Grid> =
        Try {
            val (sizeX, sizeY) = gridSizeRegex.find(grid)!!.destructured
            Grid(sizeX = sizeX.toInt(), sizeY = sizeY.toInt())
        }
    }

