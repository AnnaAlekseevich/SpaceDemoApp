package com.test.spacedemoapp.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.spacedemoapp.domain.models.RoverPhoto

@Database(entities = [RoverPhoto::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun roverPhotoDao(): RoverPhotoDao
}