//package com.test.spacedemoapp.domain.dagger
//
//import com.test.spaceapp.data.common.repositories.RemoteRoverPhotosDataStoreImpl
//import com.test.spacedemoapp.data.common.repositories.RemoteRoverPhotosDataStore
//import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStore
//import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStoreImpl
//import com.test.spacedemoapp.data.repositories.RoverPhotosRepository
//import com.test.spacedemoapp.data.repositories.RoverPhotosRepositoryImpl
//import com.test.spacedemoapp.domain.db.DatabaseHelper
//import com.test.spacedemoapp.domain.net.ApiRoverPhotos
//import dagger.Module
//import dagger.Provides
//import javax.inject.Singleton
//
//@Module
//object PhotosRepoModule {
//
//
//    @JvmStatic
//    @Singleton
//    @Provides
//    fun getRoverPhotosRepository(localRoverPhotosDataStore: LocalRoverPhotosDataStore,
//                                 remoteRoverPhotosDataStore: RemoteRoverPhotosDataStore): RoverPhotosRepository {
//        return RoverPhotosRepositoryImpl(localRoverPhotosDataStore, remoteRoverPhotosDataStore)
//    }
//
//}