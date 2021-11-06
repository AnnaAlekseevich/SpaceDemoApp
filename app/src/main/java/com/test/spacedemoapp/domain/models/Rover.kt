package com.test.spacedemoapp.domain.models

import com.google.gson.annotations.SerializedName

data class Rover(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("landing_date")
    var loadingDate: String,
    @SerializedName("launch_date")
    var launchDate: String,
    @SerializedName("status")
    var status: String
)
