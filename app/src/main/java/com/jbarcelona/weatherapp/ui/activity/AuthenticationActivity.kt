package com.jbarcelona.weatherapp.ui.activity

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.jbarcelona.weatherapp.BuildConfig
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.databinding.ActivityAuthenticationBinding
import com.jbarcelona.weatherapp.ui.fragment.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showAppVersion()
        checkPermission()
    }

    private fun checkPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun showAppVersion() {
        val appVersion = "App Version: ${BuildConfig.VERSION_NAME}"
        binding.tvAppVersion.text = appVersion
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fl_container, SignInFragment())
                    ft.commit()
                } else {
                    checkPermission()
                }
            }
        }
    }
}