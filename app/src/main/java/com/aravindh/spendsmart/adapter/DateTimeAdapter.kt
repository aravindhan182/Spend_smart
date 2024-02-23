package com.aravindh.spendsmart.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object DateTimeAdapter {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")


    @JvmStatic
    @TypeConverter
    fun fromDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @JvmStatic
    @TypeConverter
    fun toDateTime(value: String?): LocalDateTime? {
        value?.let {
            return formatter.parse(value, LocalDateTime::from)
        } ?: return null
    }
}