package com.test.spacedemoapp.domain.models

import com.google.gson.annotations.SerializedName

data class RoverPhotos(
    @SerializedName("earth_date")
    var earthDate: String,

    //title description coin
    @SerializedName("camera")
    var camera: Camera,

    //name coin
    @SerializedName("page")
    var page: Int,

    //item image coin
    @SerializedName("api_key")
    var apiKey: String
)
