package com.jbarcelona.weatherapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    fun getDateStringFromTimestamp(timestamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        return simpleDateFormat.format(timestamp * 1000L)
    }

    fun getDateStringFromTimeMillis(millis: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.ENGLISH)
        return simpleDateFormat.format(millis)
    }

    fun isPast6pm(): Boolean {
        return false
    }

    fun getCurrentDateString(): String {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format(Date())
    }
}