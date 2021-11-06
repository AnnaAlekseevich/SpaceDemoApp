package com.test.spacedemoapp.domain.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.test.spacedemoapp.domain.models.Rover

@ProvidedTypeConverter
class RoverConverter {
    @TypeConverter
    fun fromStringToRover(value: String?): Rover? {
        return Gson().fromJson(value, Rover::class.java)
    }

    @TypeConverter
    fun fromRoverToString(rover: Rover?): String? {
        val gson = Gson()
        return gson.toJson(rover)
    }
}