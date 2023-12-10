package com.jbarcelona.weatherapp.repository

import com.google.firebase.auth.FirebaseUser
import com.jbarcelona.weatherapp.constants.Constants
import com.jbarcelona.weatherapp.database.dao.WeatherHistoryDao
import com.jbarcelona.weatherapp.database.model.WeatherHistory
import com.jbarcelona.weatherapp.firebase.BaseAuthenticator
import com.jbarcelona.weatherapp.network.ApiDataSource
import com.jbarcelona.weatherapp.network.ApiResource
import com.jbarcelona.weatherapp.network.ApiService
import com.jbarcelona.weatherapp.network.response.GetWeatherResponseData
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val weatherHistoryDao: WeatherHistoryDao,
    private val apiService: ApiService,
    private val authenticator: BaseAuthenticator,
) : BaseRepository, ApiDataSource() {

    override suspend fun getWeather(lat: String, lon: String): ApiResource<GetWeatherResponseData> {
        return getResult { apiService.getWeather(lat, lon, Constants.API_KEY) }
    }

    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signInWithEmailPassword(email, password)
    }

    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        return authenticator.signUpWithEmailPassword(email, password)
    }

    override suspend fun signOut(): FirebaseUser? {
        return authenticator.signOut()
    }

    override suspend fun insertWeather(weatherHistory: WeatherHistory): Long {
        return weatherHistoryDao.insertWeather(weatherHistory)
    }

    override suspend fun getWeatherHistory(): List<WeatherHistory> {
        return weatherHistoryDao.getWeatherHistory()
    }
}