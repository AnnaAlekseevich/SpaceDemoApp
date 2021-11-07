package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.domain.db.DatabaseHelper
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRoverPhotosDataStoreImpl @Inject constructor(private val dbHelper: DatabaseHelper): LocalRoverPhotosDataStore {
    override fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Single<List<RoverPhoto>> {
        return dbHelper.getPhotos(earthDate, page, apiKey)
    }
}