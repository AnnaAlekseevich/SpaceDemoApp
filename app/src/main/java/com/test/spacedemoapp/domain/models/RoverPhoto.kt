package com.test.spacedemoapp.domain.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class RoverPhoto(
    //earth date
    @SerializedName("id")
    var id: Int,

    //camera
    @SerializedName("camera")
    var camera: Camera,

    //rover
    @SerializedName("rover")
    var rover: Rover,

    //rover photo
    @SerializedName("img_src")
    var urlItemPhoto: String
)
