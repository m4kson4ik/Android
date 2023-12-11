package com.olympia.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olympia.model.entities.DUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface IUserActivity {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePost(user: DUsers)
    @Delete
    suspend fun deletePost(user: DUsers)
    @Query("SELECT * FROM DUsers")
    fun getUsers(): Flow<List<DUsers>>
    @Query("SELECT * FROM DUsers WHERE id = :id")
    suspend fun getUser(id: Int): DUsers?
}