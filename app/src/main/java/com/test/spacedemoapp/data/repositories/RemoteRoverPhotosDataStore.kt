package com.test.spacedemoapp.data.common.repositories

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

interface RemoteRoverPhotosDataStore {
    fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Single<List<RoverPhoto>>
}