package com.gomsoo.shaken.core.database.util

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class CollectionConverter {

    @TypeConverter
    fun stringToStringList(value: String?): List<String>? = value?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun stringListToString(list: List<String>?): String? = list?.let { Json.encodeToString(it) }
}
