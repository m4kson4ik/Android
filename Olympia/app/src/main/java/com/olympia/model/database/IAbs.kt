package com.olympia.model.database

import androidx.room.*
import com.olympia.model.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IAbs {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertA(abs: DAbs)
    @Delete
    suspend fun deleteA(abs: DAbs)
    @Query("SELECT * FROM DAbs")
    fun getInfoA(): Flow<List<DAbs>>
    @Query("SELECT * FROM DAbs WHERE id = :id")
    suspend fun getRecord(id: Int): DAbs?
}