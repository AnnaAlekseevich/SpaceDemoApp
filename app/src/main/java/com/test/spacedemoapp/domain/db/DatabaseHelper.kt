package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

interface DatabaseHelper {
    fun deleteAll()
    fun getPhotos(earthDate: String, page: Int, apiKey: String): Single<List<RoverPhoto>>
}