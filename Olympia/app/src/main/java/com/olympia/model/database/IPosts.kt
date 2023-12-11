package com.olympia.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olympia.model.entities.DPosts
import kotlinx.coroutines.flow.Flow

@Dao
interface IPosts {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePost(post: DPosts)
    @Delete
    suspend fun deletePost(post: DPosts)
    @Query("SELECT * FROM DPosts")
    fun getPosts(): Flow<List<DPosts>>
    @Query("SELECT * FROM DPosts WHERE id = :id")
    suspend fun getPost(id: Int): DPosts?
}