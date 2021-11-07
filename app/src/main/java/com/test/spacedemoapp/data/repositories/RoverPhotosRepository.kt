//package com.test.spacedemoapp.data.repositories
//
//import com.test.spacedemoapp.domain.models.RoverPhoto
//import io.reactivex.Single
//
//interface RoverPhotosRepository {
//
//    fun getPhotos(
//        earthDate: String,
//        page: Int,
//        apiKey: String,
//        isHaveInternet: Boolean
//    ): Single<List<RoverPhoto>>
//
//    fun getDataFromDb(
//        earthDate: String,
//        page: Int,
//        apiKey: String
//    ): Single<List<RoverPhoto>>
//}