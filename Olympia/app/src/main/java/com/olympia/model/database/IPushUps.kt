package com.olympia.model.database

import androidx.room.*
import com.olympia.model.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IPushUps {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertP(pushUps: DPushUps)
    @Delete
    suspend fun deleteP(pushUps: DPushUps)
    @Query("SELECT * FROM DPushUps")
    fun getInfoP(): Flow<List<DPushUps>>
    @Query("SELECT * FROM DPushUps WHERE id = :id")
    suspend fun getRecord(id: Int): DPushUps?
}