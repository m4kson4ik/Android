package com.example.myrjdapi.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDAO {
    @Query("select * from station")
    fun getAllFlow() :  Flow<List<StationDB>>

    @Insert
    suspend fun insert(item: StationDB)

    @Update
    suspend fun update(item : StationDB)

    @Delete
    suspend fun deleted(item : StationDB)
}

@Dao
interface LikedDAO{
    @Insert
    suspend fun insertLikedThread(item : LikedThread)

    @Delete
    suspend fun deletedLikedThread(item : LikedThread)

    @Query("select * from likedStation")
    fun getAllFlowLikedThread() : Flow<List<LikedThread>>
}