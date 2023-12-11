package com.example.myrjdapi.Interface

import com.example.myrjdapi.Room.LikedThread
import com.example.myrjdapi.Room.StationDB
import kotlinx.coroutines.flow.Flow

interface ITrainRepositoryBasic {
    fun getAllFlow() : Flow<List<StationDB>>
    suspend fun insert(item: StationDB)
    suspend fun update(item : StationDB)
    suspend fun deleted(item : StationDB)

    suspend fun insertLikedThread(item : LikedThread)
    suspend fun deletedLikedThread(item : LikedThread)
    fun getAllFlowLikedThread() : Flow<List<LikedThread>>
}