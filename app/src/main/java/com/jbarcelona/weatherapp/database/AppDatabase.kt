package com.jbarcelona.weatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jbarcelona.weatherapp.database.dao.WeatherHistoryDao
import com.jbarcelona.weatherapp.database.model.WeatherHistory

@Database(
    version = 1,
    entities = [WeatherHistory::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherHistoryDao(): WeatherHistoryDao
}