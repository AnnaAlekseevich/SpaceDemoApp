package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single

interface DatabaseHelper {
    fun getPhotos(earthDate: String, offset: Int, apiKey: String, perPage: Int): Single<List<RoverPhoto>>
    fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>): Completable
    fun deleteAll(): Completable
}