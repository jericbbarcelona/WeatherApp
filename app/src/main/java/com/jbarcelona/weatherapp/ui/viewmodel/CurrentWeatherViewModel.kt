package com.jbarcelona.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jbarcelona.weatherapp.database.model.WeatherHistory
import com.jbarcelona.weatherapp.network.response.GetWeatherResponseData
import com.jbarcelona.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val populateDataEvent = MutableLiveData<WeatherResult>()

    fun getWeather() {
        viewModelScope.launch {
            try {
                val responseData = mainRepository.getWeather("14.5995121", "120.984222")
                if (responseData.data != null) {
                    populateDataEvent.postValue(WeatherResult.Success(responseData.data))
                    responseData.data.apply {
                        mainRepository.insertWeather(
                            WeatherHistory(
                                id = UUID.randomUUID().toString(),
                                weather = weather?.firstOrNull()?.main,
                                temperature = "${main?.temp}°",
                                timestamp = System.currentTimeMillis().toString(),
                                location = "${name}, ${sys?.country}"
                            )
                        )
                    }
                } else {
                    populateDataEvent.postValue(WeatherResult.Error(responseData.message.orEmpty()))
                }
            } catch (e: Exception) {
                populateDataEvent.postValue(WeatherResult.Error(e.message.toString()))
            }
        }
    }

    sealed class WeatherResult {
        class Success(val responseData: GetWeatherResponseData?) : WeatherResult()
        class Error(val message: String) : WeatherResult()
    }
}