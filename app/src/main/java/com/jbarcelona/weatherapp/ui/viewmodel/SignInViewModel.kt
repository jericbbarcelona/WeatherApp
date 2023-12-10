package com.jbarcelona.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jbarcelona.weatherapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {
    val signInEvent = MutableLiveData<AuthResult>()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                val firebaseUser = mainRepository.signInWithEmailPassword(email, password)
                if (firebaseUser != null) {
                    signInEvent.postValue(AuthResult.Success(firebaseUser))
                }
            } catch (e: Exception) {
                signInEvent.postValue(AuthResult.Error(e.message.toString()))
            }
        }
    }

    sealed class AuthResult {
        class Success(val firebaseUser: FirebaseUser) : AuthResult()
        class Error(val message: String) : AuthResult()
    }
}