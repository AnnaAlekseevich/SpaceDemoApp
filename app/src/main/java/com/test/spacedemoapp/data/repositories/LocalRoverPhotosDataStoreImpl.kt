package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.domain.db.DatabaseHelper
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRoverPhotosDataStoreImpl @Inject constructor(private val dbHelper: DatabaseHelper): LocalRoverPhotosDataStore {
    override suspend fun getPhotos(
        earthDate: String,
        offset: Int,
        apiKey: String,
        perPage: Int
    ): List<RoverPhoto> {
        return dbHelper.getPhotos(earthDate, offset, apiKey, perPage)
    }

    override suspend fun deleteAllPhotos() {
        return dbHelper.deleteAll()
    }

    override suspend fun insertAllPhotos(list: List<RoverPhoto>) {
        return dbHelper.insertRoverPhoto(list)
    }

}