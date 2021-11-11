package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Observable
import io.reactivex.Single
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
        apiKey: String,
        perPage: Int
    ): Single<List<RoverPhoto>> {
        return if (isInternetAvailable) {
            remoteRoverPhotosDataStore.getPhotos(earthDate, page, apiKey).flatMap {
                it.forEach { photo -> photo.dbSortPage = page }
                val dataList = it
                return@flatMap insertRoverPhotosFromApiToBD(it).andThen(
                    Single.just(it)
                ).onErrorReturn { return@onErrorReturn dataList }
            }
        } else {
            val offset = (page - 1) * perPage
            localRoverPhotosDataStore.getPhotos(
                earthDate,
                offset,
                apiKey,
                perPage
            )
        }
    }

    private fun insertRoverPhotosFromApiToBD(roverPhotoList: List<RoverPhoto>) =
        localRoverPhotosDataStore.insertAllPhotos(roverPhotoList)

}