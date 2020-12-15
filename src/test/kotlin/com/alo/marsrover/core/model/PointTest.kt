package com.alo.marsrover.core.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.alo.marsrover.core.model.Direction.*
import org.junit.Test

internal class PointTest {
    
    private val point = Point(2, 2)
    
    @Test
    fun `should move north`() {
        assertThat(point.moveTo(NORTH)).isEqualTo(Point(2, 3))
    }

    @Test
    fun `should move south`() {
        assertThat(point.moveTo(SOUTH)).isEqualTo(Point(2, 1))
    }

    @Test
    fun `should move east`() {
        assertThat(point.moveTo(EAST)).isEqualTo(Point(3, 2))
    }

    @Test
    fun `should move west`() {
        assertThat(point.moveTo(WEST)).isEqualTo(Point(1, 2))
    }
}
