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
    fun getRoverPhoto(perPage: Int, offset: Int): Single<List<RoverPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>): Completable

    @Query("DELETE FROM roverPhoto")
    fun deleteAll(): Completable
}