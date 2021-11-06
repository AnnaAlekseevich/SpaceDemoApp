package com.test.spacedemoapp.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Single

@Dao
interface RoverPhotoDao {
    @Query("SELECT * FROM roverPhoto")
    fun getRoverPhoto(): Single<List<RoverPhoto>>

    @Insert
    fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>)

    @Update(entity = RoverPhoto::class)
    fun updateRoverPhoto(roverPhotoList: List<RoverPhoto>)

    @Query("DELETE FROM roverPhoto")
    fun deleteAll()
}