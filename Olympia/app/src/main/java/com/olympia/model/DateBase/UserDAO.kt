package com.olympia.model.DateBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(vararg user : Users)

    @Query("select * from users")
    fun getListUsers() : List<Users>

    @Query("select * from users")
    fun getAllFlowUsers() : Flow<List<Users>>

    @Query("select * from users")
    fun getListFLowUser() : Flow<List<Users>>
}