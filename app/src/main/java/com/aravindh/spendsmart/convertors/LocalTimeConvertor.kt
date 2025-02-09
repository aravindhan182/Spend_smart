package com.aravindh.spendsmart.convertors

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class LocalTimeConverter {

    private val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.format(formatter)
    }

    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it, formatter) }
    }
}