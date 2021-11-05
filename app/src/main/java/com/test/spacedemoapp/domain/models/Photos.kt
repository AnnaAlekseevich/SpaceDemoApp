package com.test.spacedemoapp.domain.models

import com.google.gson.annotations.SerializedName

data class Photos(

//list Photos
@SerializedName("photos")
var photos: List<RoverPhotos>

)
