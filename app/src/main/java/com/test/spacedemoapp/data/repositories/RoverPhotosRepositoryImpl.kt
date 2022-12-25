package com.test.spacedemoapp.data.repositories

import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    override suspend fun getPhotos(
        earthDate: String,
        page: Int,
        apiKey: String,
        perPage: Int
    ): List<RoverPhoto> {
        return if (isInternetAvailable) {
            val listRoverPhoto = remoteRoverPhotosDataStore.getPhotos(earthDate, page, apiKey)
            listRoverPhoto.forEach{it.dbSortPage=page}
            insertRoverPhotosFromApiToBD(listRoverPhoto)
            listRoverPhoto
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

    private suspend fun insertRoverPhotosFromApiToBD(roverPhotoList: List<RoverPhoto>) =
        localRoverPhotosDataStore.insertAllPhotos(roverPhotoList)

}