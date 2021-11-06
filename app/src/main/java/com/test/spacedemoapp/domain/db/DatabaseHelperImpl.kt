package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override fun deleteAll() {
        appDatabase.roverPhotoDao().deleteAll()
    }

    override fun getPhotos(earthDate: String, page: Int, apiKey: String) : List<RoverPhoto> {
        return appDatabase.roverPhotoDao().getRoverPhoto()
    }
}