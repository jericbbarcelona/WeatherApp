package com.jbarcelona.weatherapp.network

import com.jbarcelona.weatherapp.network.response.GetWeatherResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiMethod.GET_WEATHER)
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String
    ): Response<GetWeatherResponseData>
}