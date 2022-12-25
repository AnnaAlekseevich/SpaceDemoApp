package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single

interface LocalRoverPhotosDataStore {
    suspend fun getPhotos(
        earthDate: String,
        offset: Int,
        apiKey: String,
        perPage: Int
    ): List<RoverPhoto>

    suspend fun deleteAllPhotos()

    suspend fun insertAllPhotos(list: List<RoverPhoto>)
}