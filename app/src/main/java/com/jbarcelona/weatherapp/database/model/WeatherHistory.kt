package com.jbarcelona.weatherapp.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WEATHER_HISTORY")
data class WeatherHistory(
    @PrimaryKey
    var id: String,
    var weather: String? = null,
    var temperature: String? = null,
    var timestamp: String? = null,
    var location: String? = null
)