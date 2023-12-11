package com.jbarcelona.weatherapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jbarcelona.weatherapp.database.model.WeatherHistory

@Dao
interface WeatherHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherHistory: WeatherHistory): Long

    @Query("SELECT * FROM WEATHER_HISTORY ORDER BY timestamp DESC")
    suspend fun getWeatherHistory(): List<WeatherHistory>
}