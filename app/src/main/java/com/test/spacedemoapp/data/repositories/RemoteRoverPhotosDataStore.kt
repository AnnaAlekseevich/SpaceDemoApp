package com.test.spacedemoapp.data.common.repositories

import com.test.spacedemoapp.domain.models.Photos
import io.reactivex.Observable


interface RemoteRoverPhotosDataStore {
    fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Observable<Photos>
}