package com.olympia.model.database

import androidx.room.*
import com.olympia.model.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ISquats {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertS(squats: DSquats)
    @Delete
    suspend fun deleteS(squats: DSquats)
    @Query("SELECT * FROM DSquats")
    fun getInfoS(): Flow<List<DSquats>>
    @Query("SELECT * FROM DSquats WHERE id = :id")
    suspend fun getRecord(id: Int): DSquats?
}