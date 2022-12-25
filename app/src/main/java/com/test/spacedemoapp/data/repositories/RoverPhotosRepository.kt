package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

interface RoverPhotosRepository {

    suspend fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String,
        perPage: Int
    ): List<RoverPhoto>

}