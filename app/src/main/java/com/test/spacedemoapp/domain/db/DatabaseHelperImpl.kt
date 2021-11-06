package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override fun deleteAll() {
        appDatabase.roverPhotoDao().deleteAll()
    }

    override fun getPhotos(earthDate: String, page: Int, apiKey: String) : Single<List<RoverPhoto>> {
        return appDatabase.roverPhotoDao().getRoverPhoto()
    }
}