package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getPhotos(
        earthDate: String,
        offset: Int,
        apiKey: String,
        perPage: Int
    ): List<RoverPhoto> {
        return appDatabase.roverPhotoDao().getRoverPhoto(perPage, offset)
    }

    override suspend fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>) {
        return appDatabase.roverPhotoDao().insertRoverPhoto(roverPhotoList)
    }

    override suspend fun deleteAll() {
        return appDatabase.roverPhotoDao().deleteAll()
    }
}