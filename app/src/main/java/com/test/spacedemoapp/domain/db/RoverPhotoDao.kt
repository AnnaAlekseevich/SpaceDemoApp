package com.test.spacedemoapp.domain.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoverPhotoDao {
    @Query("SELECT * FROM roverPhoto ORDER BY dbSortPage ASC  LIMIT :perPage OFFSET :offset ")
    suspend fun getRoverPhoto(perPage: Int, offset: Int): List<RoverPhoto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>)

    @Query("DELETE FROM roverPhoto")
    suspend fun deleteAll()
}