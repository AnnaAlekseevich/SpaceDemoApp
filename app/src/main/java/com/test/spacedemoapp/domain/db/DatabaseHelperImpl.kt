package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) : DatabaseHelper {

    override fun getPhotos(earthDate: String, page: Int, apiKey: String): Single<List<RoverPhoto>> {
        return appDatabase.roverPhotoDao().getRoverPhoto()
    }

    override fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>): Completable {
        return appDatabase.roverPhotoDao().insertRoverPhoto(roverPhotoList)
    }

    override fun deleteAll(): Completable {
        return appDatabase.roverPhotoDao().deleteAll()
    }
}