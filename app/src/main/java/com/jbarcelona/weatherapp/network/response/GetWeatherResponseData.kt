package com.jbarcelona.weatherapp.network.response

import com.squareup.moshi.Json

data class GetWeatherResponseData(
    @Json(name = "cod")
    var cod: Int? = null,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "weather")
    var weather: List<Weather>? = null,
    @Json(name = "weather")
    var main: Main? = null,
    @Json(name = "sys")
    var sys: Sys? = null,
    @Json(name = "timezone")
    var timezone: String? = null,
    @Json(name = "name")
    var name: String? = null
)

data class Weather(
    @Json(name = "id")
    var id: String? = null,
    @Json(name = "main")
    var main: String? = null,
    @Json(name = "cod")
    var description: String? = null
)

data class Main(
    @Json(name = "temp")
    var temp: String? = null
)

data class Sys(
    @Json(name = "sunrise")
    var sunrise: String? = null,
    @Json(name = "sunset")
    var sunset: String? = null,
    @Json(name = "country")
    var country: String? = null
)