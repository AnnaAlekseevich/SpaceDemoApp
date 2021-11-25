package com.test.spacedemoapp.domain.dagger

import android.app.Application
import androidx.room.Room
import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStore
import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStoreImpl
import com.test.spacedemoapp.domain.db.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .addTypeConverter(CameraConverter())
            .addTypeConverter(RoverConverter())
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideHelperDatabase(appDatabase: AppDatabase): DatabaseHelper {
        return DatabaseHelperImpl(appDatabase)
    }

    @Provides
    fun getLocaleRoverPhotosDataStore(dbHelper: DatabaseHelper): LocalRoverPhotosDataStore {
        return LocalRoverPhotosDataStoreImpl(dbHelper)
    }
}