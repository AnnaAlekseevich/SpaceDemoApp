package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single

interface LocalRoverPhotosDataStore {
    fun getPhotos(
        earthDate: String,
        offset: Int,
        apiKey: String,
        perPage: Int
    ): Single<List<RoverPhoto>>

    fun deleteAllPhotos(): Completable

    fun insertAllPhotos(list: List<RoverPhoto>): Completable
}