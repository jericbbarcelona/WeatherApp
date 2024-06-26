package com.jbarcelona.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jbarcelona.weatherapp.R
import com.jbarcelona.weatherapp.constants.Constants
import com.jbarcelona.weatherapp.database.model.WeatherHistory
import com.jbarcelona.weatherapp.util.DateUtil
import com.jbarcelona.weatherapp.util.TemperatureUtil

class WeatherHistoryAdapter(
    var weatherHistoryList: List<WeatherHistory>
) : RecyclerView.Adapter<WeatherHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = weatherHistoryList[position]
        val temperature = "${TemperatureUtil.convertFromKelvinToCelsius(item.temperature.orEmpty())}°"
        holder.tvWeather.text = item.weather
        holder.tvTemperature.text = temperature
        holder.tvLocation.text = item.location
        holder.tvTimestamp.text = DateUtil.getDateStringFromTimeMillis(item.timestamp?.toLong() ?: 0L)
        when (item.weather?.uppercase()) {
            Constants.RAIN -> setImageViewDrawable(holder.ivWeather, R.drawable.raining)
            Constants.CLOUDS -> setImageViewDrawable(holder.ivWeather, R.drawable.cloudy)
            Constants.SUN -> setImageViewDrawable(holder.ivWeather, R.drawable.sun)
            Constants.CLEAR -> setImageViewDrawable(holder.ivWeather, R.drawable.clear)
            Constants.THUNDERSTORM -> setImageViewDrawable(holder.ivWeather, R.drawable.storm)
            Constants.SNOW -> setImageViewDrawable(holder.ivWeather, R.drawable.snowflake)
            Constants.DRIZZLE -> setImageViewDrawable(holder.ivWeather, R.drawable.drizzle)
        }
    }

    private fun setImageViewDrawable(imageView: ImageView, resId: Int) {
        imageView.setImageResource(resId);
    }

    override fun getItemCount() = weatherHistoryList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTemperature: TextView = view.findViewById(R.id.tv_temperature)
        val tvWeather: TextView = view.findViewById(R.id.tv_weather)
        val tvTimestamp: TextView = view.findViewById(R.id.tv_timestamp)
        val tvLocation: TextView = view.findViewById(R.id.tv_location)
        val ivWeather: ImageView = view.findViewById(R.id.iv_weather)
    }
}