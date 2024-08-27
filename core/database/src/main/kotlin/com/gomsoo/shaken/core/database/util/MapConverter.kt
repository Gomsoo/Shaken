package com.gomsoo.shaken.core.database.util

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MapConverter {

    @TypeConverter
    fun fromString(value: String?): Map<String, String>? = value?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun toString(map: Map<String, String>?): String? = map?.let { Json.encodeToString(it) }
}
