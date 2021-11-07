package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val appDatabase: AppDatabase) : DatabaseHelper {
    override fun deleteAll() {
        appDatabase.roverPhotoDao().deleteAll()
    }

    override fun getPhotos(earthDate: String, page: Int, apiKey: String) : Single<List<RoverPhoto>> {
        return appDatabase.roverPhotoDao().getRoverPhoto()
    }
}