package com.jbarcelona.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.jbarcelona.weatherapp.network.response.GetWeatherResponseData
import com.jbarcelona.weatherapp.ui.viewmodel.CurrentWeatherViewModel
import com.jbarcelona.weatherapp.util.DateUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : BaseFragment() {

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var binding: FragmentCurrentWeatherBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentCurrentWeatherBinding>(
            inflater,
            R.layout.fragment_current_weather,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
            this.viewModel = viewModel
        }
        setProgressBarVisibility(true)
        viewModel.getWeather()
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.populateDataEvent.observe(viewLifecycleOwner) {
            setProgressBarVisibility(false)
            when (it) {
                is CurrentWeatherViewModel.WeatherResult.Success -> {
                    binding.tvErrorMessage.visibility = View.GONE
                    populateWeatherData(it.responseData)
                }
                is CurrentWeatherViewModel.WeatherResult.Error -> {
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.tvErrorMessage.text = it.message
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun populateWeatherData(responseData: GetWeatherResponseData?) {
        responseData?.apply {
            val temperature = "${main?.temp}°"
            val location = "${name}, ${sys?.country}"
            binding.tvTemperature.text = temperature
            binding.tvWeather.text = weather?.firstOrNull()?.main
            binding.tvLocation.text = location
            binding.llSunrise.visibility = View.VISIBLE
            binding.llSunset.visibility = View.VISIBLE
            binding.tvSunriseValue.text = DateUtil.getDateStringFromTimestamp(sys?.sunrise?.toLong() ?: 0L)
            binding.tvSunsetValue.text = DateUtil.getDateStringFromTimestamp(sys?.sunset?.toLong() ?: 0L)
        }
    }


    private fun setProgressBarVisibility(visible: Boolean) {
        if (visible) {
            binding.rlProgressbar.visibility = View.VISIBLE
        } else {
            binding.rlProgressbar.visibility = View.GONE
        }
    }
}