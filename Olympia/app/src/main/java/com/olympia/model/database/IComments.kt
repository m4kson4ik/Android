package com.olympia.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olympia.model.entities.DComments
import kotlinx.coroutines.flow.Flow

@Dao
interface IComments {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePW(comment: DComments)
    @Delete
    suspend fun delete(comment: DComments)
    @Query("SELECT * FROM DComments")
    fun getData(): Flow<List<DComments>>
    @Query("SELECT * FROM DComments WHERE id = :id")
    suspend fun getPW(id: Int): DComments?
}