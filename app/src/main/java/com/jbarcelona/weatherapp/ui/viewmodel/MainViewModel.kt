package com.jbarcelona.weatherapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.jbarcelona.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    fun signOut() {
        viewModelScope.launch {
            mainRepository.signOut()
        }
    }
}