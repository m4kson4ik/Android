package com.olympia.model.database

import androidx.room.*
import com.olympia.model.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IUsers {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: DUsers)
    @Delete
    suspend fun deleteUser(user: DUsers)
    @Query("SELECT * FROM DUsers")
    fun getUsers(): Flow<List<DUsers>>
    @Query("SELECT * FROM DUsers WHERE id = :id")
    suspend fun getRecord(id: Int?): DUsers?
}