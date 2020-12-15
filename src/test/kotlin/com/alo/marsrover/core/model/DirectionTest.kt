package com.alo.marsrover.core.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class DirectionTest {

    @ParameterizedTest
    @CsvSource("NORTH,EAST", "EAST,SOUTH", "SOUTH,WEST", "WEST, NORTH")
    fun `should calculate the right direction of a direction`(input: String, expected: String) {
        assertThat(Direction.valueOf(input).right()).isEqualTo(Direction.valueOf(expected))
    }

    @ParameterizedTest
    @CsvSource("NORTH,WEST", "WEST,SOUTH", "SOUTH,EAST", "EAST, NORTH")
    fun `should calculate the left direction of a direction`(input: String, expected: String) {
        assertThat(Direction.valueOf(input).left()).isEqualTo(Direction.valueOf(expected))
    }

    @ParameterizedTest
    @CsvSource("NORTH,SOUTH", "EAST,WEST", "SOUTH,NORTH", "WEST, EAST")
    fun `should calculate the opposite direction of a direction`(input: String, expected: String) {
        assertThat(Direction.valueOf(input).opposite()).isEqualTo(Direction.valueOf(expected))
    }


}
