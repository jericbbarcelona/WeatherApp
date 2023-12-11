package com.jbarcelona.weatherapp.util

import org.junit.Assert.*

import org.junit.Test

class TemperatureUtilTest {

    @Test
    fun convertFromKelvinToCelsius() {
        val result = TemperatureUtil.convertFromKelvinToCelsius("315.15")
        assertEquals("42", result)
    }
}