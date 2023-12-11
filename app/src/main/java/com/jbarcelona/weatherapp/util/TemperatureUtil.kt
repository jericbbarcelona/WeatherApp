package com.jbarcelona.weatherapp.util

import kotlin.math.roundToInt

object TemperatureUtil {

    fun convertFromKelvinToCelsius(temperature: String): String {
        val result = temperature.toFloat() - 273.15
        return result.roundToInt().toString()
    }
}