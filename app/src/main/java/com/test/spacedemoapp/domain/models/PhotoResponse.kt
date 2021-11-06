package com.test.spacedemoapp.domain.models

import com.google.gson.annotations.SerializedName

data class PhotoResponse(

//list Photos
@SerializedName("photos")
var photos: List<RoverPhoto>

)
