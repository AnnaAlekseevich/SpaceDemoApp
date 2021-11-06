package com.test.spacedemoapp.domain.net

import com.test.spacedemoapp.domain.models.PhotoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRoverPhotos {

    @GET("api/v1/rovers/curiosity/photos")
    fun getPhotos(
        @Query("earth_date") earth_date: String,
        @Query("camera") camera: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Single<PhotoResponse>

}