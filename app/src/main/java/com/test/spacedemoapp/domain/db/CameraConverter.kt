package com.test.spacedemoapp.domain.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.test.spacedemoapp.domain.models.RoverCamera

@ProvidedTypeConverter
class CameraConverter {
    @TypeConverter
    fun fromStringToCamera(value: String?): RoverCamera? {
        return Gson().fromJson(value, RoverCamera::class.java)
    }

    @TypeConverter
    fun fromCameraToString(roverCamera: RoverCamera?): String? {
        val gson = Gson()
        return gson.toJson(roverCamera)
    }
}