package com.test.spacedemoapp.data.common.repositories

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

interface RemoteRoverPhotosDataStore {
    suspend fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): List<RoverPhoto>
}