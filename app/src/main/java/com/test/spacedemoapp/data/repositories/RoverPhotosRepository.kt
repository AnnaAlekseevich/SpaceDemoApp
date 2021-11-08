package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

interface RoverPhotosRepository {
    var isInternetAvailable: Boolean

    fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Single<List<RoverPhoto>>

}