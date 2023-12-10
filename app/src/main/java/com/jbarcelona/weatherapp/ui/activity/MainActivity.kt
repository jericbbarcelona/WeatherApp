package com.jbarcelona.weatherapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.databinding.ActivityMainBinding
import com.jbarcelona.weatherapp.ui.fragment.CurrentWeatherFragment
import com.jbarcelona.weatherapp.ui.fragment.WeatherHistoryFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(CurrentWeatherFragment())
        initNavigationListener()
    }

    private fun initNavigationListener() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> {
                    loadFragment(CurrentWeatherFragment())
                    true
                }
                R.id.bottom_history -> {
                    loadFragment(WeatherHistoryFragment())
                    true
                }
                else -> {
                    loadFragment(CurrentWeatherFragment())
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.commit()
    }
}