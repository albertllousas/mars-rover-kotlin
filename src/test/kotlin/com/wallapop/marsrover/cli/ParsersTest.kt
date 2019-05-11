package com.wallapop.marsrover.cli

import arrow.core.Failure
import arrow.core.Try
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.wallapop.marsrover.core.model.Direction
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.Point
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.junit.jupiter.params.provider.CsvSource



internal class ParsersTest {

    @Nested
    inner class GridParsingTest {

        private val parse:DomainParser<Grid> = Parsers::parseGrid

        @Test
        fun `should parse a valid grid`() {
            assertThat(parse("10 20")).isEqualTo(Try.Success(Grid(10, 20)))
        }

        @ParameterizedTest
        @ValueSource(strings = ["invalid", "1.1 20", "1", "-9 4"])
        fun `should fail parsing an invalid grid size`(input: String) {
            assertThat(parse(input).isFailure()).isTrue()
        }
    }

    @Nested
    inner class PointParsingTest {

        private val parse:DomainParser<Point> = Parsers::parsePoint

        @Test
        fun `should parse a valid point`() {
            assertThat(parse("2 3")).isEqualTo(Try.Success(Point(2, 3)))
        }

        @ParameterizedTest
        @ValueSource(strings = ["invalid", "(a,b)", "1,4", "-9 4"])
        fun `should fail parsing an invalid point`(input: String) {
            assertThat(parse(input).isFailure()).isTrue()
        }
    }

    @Nested
    inner class DirectionParsingTest {

        private val parse:DomainParser<Direction> = Parsers::parseDirection

        @ParameterizedTest
        @CsvSource("E,EAST", "N,NORTH", "W,WEST", "S, SOUTH")
        fun `should parse a valid direction`(input: String, expected: String) {
            assertThat(parse(input)).isEqualTo(Try.Success(Direction.valueOf(expected)))
        }

        @ParameterizedTest
        @ValueSource(strings = ["invalid", "n", "North", "NORTH"])
        fun `should fail parsing an invalid point`(input: String) {
            assertThat(parse(input).isFailure()).isTrue()
        }
    }

}
