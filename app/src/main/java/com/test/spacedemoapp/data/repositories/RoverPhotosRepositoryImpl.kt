package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single
import javax.inject.Inject

class RoverPhotosRepositoryImpl @Inject constructor(
    private val localRoverPhotosDataStore: LocalRoverPhotosDataStore,
    private val remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore
) : RoverPhotosRepository {
    override fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String,
        isHaveInternet: Boolean
    ): Single<List<RoverPhoto>> {
        return if (isHaveInternet) {
            localRoverPhotosDataStore.getPhotos(earthDate, page, apiKey)
        } else {
            remoteRoverPhotosDataStore.getPhotos(
                earthDate,
                page,
                apiKey
            )
        }
    }

    override fun getDataFromDb(earthDate: String, page: Int, apiKey: String): Single<List<RoverPhoto>> {
        return localRoverPhotosDataStore.getPhotos(earthDate, page, apiKey)
    }
}