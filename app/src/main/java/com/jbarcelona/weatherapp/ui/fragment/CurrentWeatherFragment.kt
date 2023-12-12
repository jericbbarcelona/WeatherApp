package com.jbarcelona.weatherapp.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.constants.Constants
import com.jbarcelona.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.jbarcelona.weatherapp.network.response.GetWeatherResponseData
import com.jbarcelona.weatherapp.ui.viewmodel.CurrentWeatherViewModel
import com.jbarcelona.weatherapp.util.DateUtil
import com.jbarcelona.weatherapp.util.TemperatureUtil
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CurrentWeatherFragment : BaseFragment() {

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var binding: FragmentCurrentWeatherBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentCurrentWeatherBinding>(
            inflater,
            R.layout.fragment_current_weather,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
        }
        getLastLocation()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.populateDataEvent.observe(viewLifecycleOwner) {
            setProgressBarVisibility(false)
            when (it) {
                is CurrentWeatherViewModel.WeatherResult.Success -> {
                    binding.tvErrorMessage.visibility = View.GONE
                    binding.ivError.visibility = View.GONE
                    populateWeatherData(it.responseData)
                }
                is CurrentWeatherViewModel.WeatherResult.Error -> {
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.ivError.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = it.message
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun populateWeatherData(responseData: GetWeatherResponseData?) {
        responseData?.apply {
            val temperature = "${TemperatureUtil.convertFromKelvinToCelsius(main?.temp.orEmpty())}°C"
            val location = "${name}, ${sys?.country}"
            val weather = weather?.firstOrNull()?.main.orEmpty()
            binding.tvTemperature.text = temperature
            binding.tvWeather.text = weather
            binding.tvLocation.text = location
            binding.llSunrise.visibility = View.VISIBLE
            binding.llSunset.visibility = View.VISIBLE
            binding.tvSunriseValue.text = DateUtil.getDateStringFromTimestamp(sys?.sunrise?.toLong() ?: 0L)
            binding.tvSunsetValue.text = DateUtil.getDateStringFromTimestamp(sys?.sunset?.toLong() ?: 0L)
            binding.ivWeather.visibility = View.VISIBLE
            binding.ivLocation.visibility = View.VISIBLE
            val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            val currentTime: Date = Calendar.getInstance().time
            val now = simpleDateFormat.format(currentTime)
            if (DateUtil.isPast6pm(now)) {
                setImageViewDrawable(binding.ivWeather, R.drawable.moon)
            } else {
                when (weather.uppercase()) {
                    Constants.RAIN -> setImageViewDrawable(binding.ivWeather, R.drawable.raining)
                    Constants.CLOUDS -> setImageViewDrawable(binding.ivWeather, R.drawable.cloudy)
                    Constants.SUN -> setImageViewDrawable(binding.ivWeather, R.drawable.sun)
                    Constants.CLEAR -> setImageViewDrawable(binding.ivWeather, R.drawable.clear)
                    Constants.THUNDERSTORM -> setImageViewDrawable(binding.ivWeather, R.drawable.storm)
                    Constants.DRIZZLE -> setImageViewDrawable(binding.ivWeather, R.drawable.drizzle)
                    Constants.SNOW -> setImageViewDrawable(binding.ivWeather, R.drawable.snowflake)
                }
            }
        }
    }

    private fun setImageViewDrawable(imageView: ImageView, resId: Int) {
        imageView.setImageResource(resId);
    }

    private fun setProgressBarVisibility(visible: Boolean) {
        if (visible) {
            binding.rlProgressbar.visibility = View.VISIBLE
        } else {
            binding.rlProgressbar.visibility = View.GONE
        }
    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    setProgressBarVisibility(true)
                    viewModel.getWeather(it.longitude.toString(), it.latitude.toString())
                } else {
                    showLocationError(getString(R.string.current_weather_location_not_found_message))
                }
            }
        } else {
            showLocationError(getString(R.string.current_weather_location_not_found_permission_denied_message))
        }
    }

    private fun showLocationError(message: String) {
        binding.ivError.visibility = View.VISIBLE
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = message
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}