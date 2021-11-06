package com.test.spacedemoapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.test.spacedemoapp.domain.db.CameraConverter
import com.test.spacedemoapp.domain.db.RoverConverter

@Entity
data class RoverPhoto(
    //id
    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    //camera
    @TypeConverters(CameraConverter::class)
    @SerializedName("camera")
    var roverCamera: RoverCamera,

    //rover
    @TypeConverters(RoverConverter::class)
    @SerializedName("rover")
    var rover: Rover,

    //rover photo
    @SerializedName("img_src")
    var urlItemPhoto: String
)
