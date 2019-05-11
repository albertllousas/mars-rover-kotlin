package com.wallapop.marsrover.cli

import arrow.core.Failure
import arrow.core.Try
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.wallapop.marsrover.core.model.Grid
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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
        fun testSquares(input: String) {
            assertThat(parse(input)).isInstanceOf(Failure::class)
        }
    }



}
