package com.jbarcelona.weatherapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.ui.fragment.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_container, SignInFragment())
        ft.commit()
    }
}