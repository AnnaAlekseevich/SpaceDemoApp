package com.test.spacedemoapp.data.repositories

import android.util.Log
import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RoverPhotosRepositoryImpl @Inject constructor(
    private val localRoverPhotosDataStore: LocalRoverPhotosDataStore,
    private val remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore
) : RoverPhotosRepository {

    override var isInternetAvailable: Boolean = false

    override fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Single<List<RoverPhoto>> {
        Log.d("CHECKRESPONCE", "isInternetAvailable = $isInternetAvailable")
        return if (isInternetAvailable) {
            remoteRoverPhotosDataStore.getPhotos(earthDate, page, apiKey).flatMap {
                //add data from Internet to BD
                return@flatMap insertRoverPhotosFromApiToBD(it).andThen(Single.just(it))
            }
        } else {
            localRoverPhotosDataStore.getPhotos(
                earthDate,
                page,
                apiKey
            )
        }
    }

    private fun insertRoverPhotosFromApiToBD(roverPhotoList: List<RoverPhoto>): Completable {
        return localRoverPhotosDataStore.insertAllPhotos(roverPhotoList)
            .subscribeOn(Schedulers.io())
    }

}