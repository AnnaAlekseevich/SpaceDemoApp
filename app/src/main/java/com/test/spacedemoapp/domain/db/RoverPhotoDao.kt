package com.test.spacedemoapp.domain.db

import androidx.room.*
import com.test.spacedemoapp.domain.models.RoverPhoto
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoverPhotoDao {
    @Query("SELECT * FROM roverPhoto")
    fun getRoverPhoto(): Single<List<RoverPhoto>>

    @Insert
    fun insertRoverPhoto(roverPhotoList: List<RoverPhoto>): Completable

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
//    @Update(entity = RoverPhoto::class)
//    fun updateRoverPhoto(roverPhotoList: Single<List<RoverPhoto>>): Completable

    @Query("DELETE FROM roverPhoto")
    fun deleteAll(): Completable
}