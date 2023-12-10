package com.jbarcelona.weatherapp.network.response

import com.jbarcelona.weatherapp.network.model.Weather

data class GetWeatherResponseData(
    var cod: String? = null,
    var weather: Weather? = null
)