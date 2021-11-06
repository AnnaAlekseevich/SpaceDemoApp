package com.test.spacedemoapp.domain.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class RoverCamera(
    @ColumnInfo(name = "full_Name")
    @SerializedName("full_name")
    var fullName: String
)
