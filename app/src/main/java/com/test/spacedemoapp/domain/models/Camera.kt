package com.test.spacedemoapp.domain.models

import com.google.gson.annotations.SerializedName

data class Camera(
//    @SerializedName("id")
//    var id: String,
//    @SerializedName("name")
//    var name: String,
//    @SerializedName("rover_id")
//    var roverId: String,
    @SerializedName("full_name")
    var fullCameraName: String
)
