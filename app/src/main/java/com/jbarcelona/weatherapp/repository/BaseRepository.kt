package com.jbarcelona.weatherapp.repository

import com.google.firebase.auth.FirebaseUser
import com.jbarcelona.weatherapp.network.ApiResource
import com.jbarcelona.weatherapp.network.response.GetWeatherResponseData

interface BaseRepository {
    // API
    suspend fun getWeather(lat: String, long: String): ApiResource<GetWeatherResponseData>

    // Firebase
    suspend fun signInWithEmailPassword(email:String , password:String): FirebaseUser?
    suspend fun signUpWithEmailPassword(email: String , password: String): FirebaseUser?
    suspend fun signOut() : FirebaseUser?
}