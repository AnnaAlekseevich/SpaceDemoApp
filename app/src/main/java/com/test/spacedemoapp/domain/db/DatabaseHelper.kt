package com.test.spacedemoapp.domain.db

import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single

interface DatabaseHelper {
    suspend fun getPhotos(earthDate: String, offset: Int, apiKey: String, perPage: Int): List<RoverPhoto>
    suspend fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>)//: Completable
    suspend fun deleteAll()//: Completable
}