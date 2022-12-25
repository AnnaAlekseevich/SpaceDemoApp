package com.test.spaceapp.data.common.repositories

import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import com.test.spacedemoapp.domain.net.ApiRoverPhotos
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRoverPhotosDataStoreImpl
@Inject constructor(private val apiRoverPhotos: ApiRoverPhotos) : RemoteRoverPhotosDataStore {
    override suspend fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): List<RoverPhoto> {
        return apiRoverPhotos.getPhotos(earthDate, page, apiKey).photos

    }

}