package com.test.spacedemoapp.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.spacedemoapp.domain.models.RoverPhoto

@Database(entities = [RoverPhoto::class], version = 2, exportSchema = false)
@TypeConverters(CameraConverter::class, RoverConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun roverPhotoDao(): RoverPhotoDao
}