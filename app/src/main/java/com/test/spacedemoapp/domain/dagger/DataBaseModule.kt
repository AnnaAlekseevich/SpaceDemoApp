package com.test.spacedemoapp.domain.dagger

import android.app.Application
import androidx.room.Room
import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStore
import com.test.spacedemoapp.data.repositories.LocalRoverPhotosDataStoreImpl
import com.test.spacedemoapp.domain.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DataBaseModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .addTypeConverter(CameraConverter())
            .addTypeConverter(RoverConverter())
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideHelperDatabase(appDatabase: AppDatabase): DatabaseHelper {
        return DatabaseHelperImpl(appDatabase)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun getLocaleRoverPhotosDataStore(dbHelper: DatabaseHelper): LocalRoverPhotosDataStore {
        return LocalRoverPhotosDataStoreImpl(dbHelper)
    }
}