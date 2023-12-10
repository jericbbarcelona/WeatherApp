package com.jbarcelona.weatherapp.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {

    fun getDateStringFromTimestamp(timestamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.ENGLISH)
        return simpleDateFormat.format(timestamp * 1000L)
    }
}