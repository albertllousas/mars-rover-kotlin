package com.wallapop.marsrover.cli

import arrow.core.Either
import arrow.core.Failure
import arrow.core.Try
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.wallapop.marsrover.core.model.Direction
import com.wallapop.marsrover.core.model.Grid
import com.wallapop.marsrover.core.model.MoveBackward
import com.wallapop.marsrover.core.model.MoveForward
import com.wallapop.marsrover.core.model.Point
import com.wallapop.marsrover.core.model.RoverCommand
import com.wallapop.marsrover.core.model.TurnLeft
import com.wallapop.marsrover.core.model.TurnRight
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
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

        @Test
        fun `should fail parsing an invalid direction`() {
            assertThat(parse("invalid").isFailure()).isTrue()
        }
    }

    @Nested
    inner class CommandParsingTest {

        private val parse = Parsers::parseCommand

        @TestFactory
        fun `should parse valid single command`() = listOf(
            'f' to Try.just(MoveForward),
            'b' to Try.just(MoveBackward),
            'l' to Try.just(TurnLeft),
            'r' to Try.just(TurnRight)
        ).map { (input, expected) ->
            dynamicTest("should parse a string '$input' to '$expected'") {
                assertThat(parse(input)).isEqualTo(expected)
            }
        }

        @Test
        fun `should fail parsing an invalid single command`() {
            assertThat(parse('i').isFailure()).isTrue()
        }
    }

}
