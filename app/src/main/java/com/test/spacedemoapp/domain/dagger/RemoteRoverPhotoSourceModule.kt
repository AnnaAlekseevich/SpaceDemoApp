package com.test.spacedemoapp.domain.dagger

import com.test.spaceapp.data.common.repositories.RemoteRoverPhotosDataStoreImpl
import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
import com.test.spacedemoapp.domain.net.ApiRoverPhotos
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RemoteRoverPhotoSourceModule {

    @JvmStatic
    @Singleton
    @Provides
    fun getRemoteRoverPhotosDataStore(apiRoverPhotos: ApiRoverPhotos): RemoteRoverPhotosDataStore {
        return RemoteRoverPhotosDataStoreImpl(apiRoverPhotos)
    }

}