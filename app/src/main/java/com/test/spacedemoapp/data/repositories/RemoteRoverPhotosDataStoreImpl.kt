package com.test.spaceapp.data.common.repositories

import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.Photos
import com.test.spacedemoapp.domain.net.ApiRoverPhotos
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RemoteRoverPhotosDataStoreImpl @Inject constructor(private val apiRoverPhotos: ApiRoverPhotos) :
    RemoteRoverPhotosDataStore {
    override fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Observable<Photos> {
        return apiRoverPhotos.getPhotos(earthDate, page, apiKey)
            .subscribeOn(Schedulers.io())
    }

}