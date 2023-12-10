package com.jbarcelona.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jbarcelona.weatherapp.database.model.WeatherHistory
import com.jbarcelona.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val populateWeatherHistoryEvent = MutableLiveData<List<WeatherHistory>>()

    fun getWeatherHistory() {
        viewModelScope.launch {
            val weatherHistoryList = mainRepository.getWeatherHistory()
            populateWeatherHistoryEvent.postValue(weatherHistoryList)
        }
    }
}