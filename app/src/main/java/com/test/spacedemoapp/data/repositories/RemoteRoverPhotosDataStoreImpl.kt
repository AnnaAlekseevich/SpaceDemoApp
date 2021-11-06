package com.test.spaceapp.data.common.repositories

import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import com.test.spacedemoapp.domain.net.ApiRoverPhotos
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoteRoverPhotosDataStoreImpl
@Inject constructor(private val apiRoverPhotos: ApiRoverPhotos) : RemoteRoverPhotosDataStore {
    override fun getPhotos(
        earthDate: String,
        camera: String,
        page: Int,
        apiKey: String
    ): Single<List<RoverPhoto>> {
        return apiRoverPhotos.getPhotos(earthDate, camera, page, apiKey)
            .map { response -> response.photos }
            .subscribeOn(Schedulers.io())
    }

}