package org.yalab.milk.data

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LightTest{
    lateinit var light:Light

    @Before
    @Throws(Exception::class)
    fun setUp() {
        light = Light()
    }

    @Test
    fun on() {
        assertEquals(true, light.on())
    }
}
