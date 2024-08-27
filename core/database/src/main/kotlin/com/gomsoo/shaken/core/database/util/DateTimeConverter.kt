package com.gomsoo.shaken.core.database.util

import androidx.room.TypeConverter
import java.time.Instant

internal class DateTimeConverter {

    @TypeConverter
    fun stringToInstant(value: String?): Instant? = value?.let {
        val split = it.split("/")
        split.getOrNull(0)?.toLong()?.let { seconds ->
            val nanoAdjustment = split.getOrNull(1)?.toLong() ?: 0L
            Instant.ofEpochSecond(seconds, nanoAdjustment)
        }
    }

    @TypeConverter
    fun instantToLong(instant: Instant?): String? = instant?.let {
        "${it.epochSecond}/${it.nano}"
    }
}
