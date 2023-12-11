package com.example.restapi.DateBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDAO {
    @Query("select * from cat")
    fun getAllFlow() : Flow<List<CatDB>>

    @Insert
    suspend fun insert(vararg item: CatDB)

    @Update
    suspend fun update(item : CatDB)

    @Query("DELETE FROM cat")
    suspend fun deleted()
}