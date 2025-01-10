package com.rure.knr_takingattendance.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rure.knr_takingattendance.data.entities.Position
import java.time.LocalDate

class PositionMapConverter {
    @TypeConverter
    fun fromString(value: String?): Map<Position, Boolean> {
        val mapType = object : TypeToken<Map<Position, Boolean>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun mapToString(map: Map<Position, Boolean>): String? {
        val gson = Gson()
        return gson.toJson(map)
    }
}