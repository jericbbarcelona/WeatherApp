package com.jbarcelona.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.database.model.WeatherHistory

class WeatherHistoryAdapter(
    var weatherHistoryList: List<WeatherHistory>
) : RecyclerView.Adapter<WeatherHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = weatherHistoryList[position]
        holder.tvWeather.text = item.weather
        holder.tvTemperature.text = item.temperature
        holder.tvLocation.text = item.location
    }

    override fun getItemCount() = weatherHistoryList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTemperature: TextView = view.findViewById(R.id.tv_temperature)
        val tvWeather: TextView = view.findViewById(R.id.tv_weather)
        val tvLocation: TextView = view.findViewById(R.id.tv_location)
    }
}