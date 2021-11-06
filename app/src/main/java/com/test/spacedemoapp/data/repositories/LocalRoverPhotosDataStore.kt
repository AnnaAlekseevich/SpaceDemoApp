package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

interface LocalRoverPhotosDataStore {
    fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Single<List<RoverPhoto>>
}