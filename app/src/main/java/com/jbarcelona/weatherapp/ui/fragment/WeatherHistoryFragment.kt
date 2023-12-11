package com.jbarcelona.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.databinding.FragmentWeatherHistoryBinding
import com.jbarcelona.weatherapp.ui.adapter.WeatherHistoryAdapter
import com.jbarcelona.weatherapp.ui.viewmodel.WeatherHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherHistoryFragment : BaseFragment() {

    private lateinit var viewModel: WeatherHistoryViewModel
    private lateinit var binding: FragmentWeatherHistoryBinding
    private lateinit var weatherHistoryLayoutManager: RecyclerView.LayoutManager
    private lateinit var weatherHistoryAdapter: WeatherHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(WeatherHistoryViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentWeatherHistoryBinding>(
            inflater,
            R.layout.fragment_weather_history,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
        }
        initAdapter()
        viewModel.getWeatherHistory()
        setupObservers()
        return binding.root
    }

    private fun initAdapter() {
        weatherHistoryLayoutManager = LinearLayoutManager(requireContext())
        weatherHistoryAdapter = WeatherHistoryAdapter(emptyList())
        binding.rvWeatherHistory.layoutManager = weatherHistoryLayoutManager
        binding.rvWeatherHistory.adapter = weatherHistoryAdapter
    }

    private fun setupObservers() {
        viewModel.populateWeatherHistoryEvent.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.llContent.visibility = View.VISIBLE
                binding.tvMessage.visibility = View.GONE
                weatherHistoryAdapter.weatherHistoryList = it.orEmpty()
                weatherHistoryAdapter.notifyDataSetChanged()
            } else {
                binding.llContent.visibility = View.GONE
                binding.tvMessage.visibility = View.VISIBLE
            }
        }
    }
}