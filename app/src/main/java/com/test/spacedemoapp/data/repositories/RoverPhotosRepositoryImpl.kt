package com.test.spacedemoapp.data.repositories

import android.util.Log
import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RoverPhotosRepositoryImpl @Inject constructor(
    private val localRoverPhotosDataStore: LocalRoverPhotosDataStore,
    private val remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore,
    private val internetStateObservable: Observable<Boolean>
) : RoverPhotosRepository {


    private var isInternetAvailable: Boolean = false

    init {
        internetStateObservable.subscribe { newInternetState ->
            isInternetAvailable = newInternetState
        }
    }


    override fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String
    ): Single<List<RoverPhoto>> {
        return if (isInternetAvailable) {
            remoteRoverPhotosDataStore.getPhotos(earthDate, page, apiKey).flatMap {
                val dataList = it
                return@flatMap insertRoverPhotosFromApiToBD(it).andThen(
                    Single.just(it)
                ).onErrorReturn { return@onErrorReturn dataList }
            }
        } else {
            localRoverPhotosDataStore.getPhotos(
                earthDate,
                page,
                apiKey
            ).flatMap {
                return@flatMap Single.just(it)
            }
        }
    }

    private fun insertRoverPhotosFromApiToBD(roverPhotoList: List<RoverPhoto>): Completable {
        localRoverPhotosDataStore.deleteAllPhotos().subscribeOn(Schedulers.io())
        return localRoverPhotosDataStore.insertAllPhotos(roverPhotoList)
            .subscribeOn(Schedulers.io())
    }

}