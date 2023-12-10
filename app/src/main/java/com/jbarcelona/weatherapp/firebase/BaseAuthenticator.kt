package com.jbarcelona.weatherapp.firebase

import com.google.firebase.auth.FirebaseUser

interface BaseAuthenticator {

    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signOut(): FirebaseUser?
}