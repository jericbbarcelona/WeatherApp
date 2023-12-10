package com.jbarcelona.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jbarcelona.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val signUpEvent = MutableLiveData<AuthResult>()

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            try {
                val firebaseUser = mainRepository.signUpWithEmailPassword(email, password)
                if (firebaseUser != null) {
                    signUpEvent.postValue(AuthResult.Success(firebaseUser))
                }
            } catch (e: Exception) {
                signUpEvent.postValue(AuthResult.Error(e.message.toString()))
            }
        }
    }

    sealed class AuthResult {
        class Success(val firebaseUser: FirebaseUser) : AuthResult()
        class Error(val message: String) : AuthResult()
    }
}