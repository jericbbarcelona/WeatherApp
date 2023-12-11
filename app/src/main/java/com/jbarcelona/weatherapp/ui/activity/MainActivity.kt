package com.jbarcelona.weatherapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.databinding.ActivityMainBinding
import com.jbarcelona.weatherapp.ui.fragment.CurrentWeatherFragment
import com.jbarcelona.weatherapp.ui.fragment.WeatherHistoryFragment
import com.jbarcelona.weatherapp.ui.viewmodel.MainViewModel
import com.jbarcelona.weatherapp.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
        }
        displayDate()
        loadFragment(CurrentWeatherFragment())
        initNavigationListener()
        initListeners()
    }

    private fun initListeners() {
        binding.ivLogout.setOnClickListener {
            displayAlertDialog()
        }
    }

    private fun displayDate() {
        binding.tvDate.text = DateUtil.getCurrentDateString()
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
        transaction.replace(R.id.fl_content, fragment)
        transaction.commit()
    }

    private fun displayAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(getString(R.string.global_logout_alert_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.global_logout_option_yes)) { _, _ ->
                performSignOut()
            }
            .setNegativeButton(getString(R.string.global_logout_option_cancel)) { dialog, _ ->
                dialog.cancel()
            }
        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.global_logout_alert_title))
        alert.show()
    }

    private fun performSignOut() {
        viewModel.signOut()
        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        finish()
        startActivity(intent)
    }

    override fun onBackPressed() {}

}