package com.example.myapplication.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

    @Dao
    interface AuditionBaseDAO
    {
        @Insert
        suspend fun insert(vararg auditionDB: AuditionDB)

        @Update
        suspend fun update(auditionDB: AuditionDB)

        @Delete
        suspend fun deleted(vararg auditionDB: AuditionDB)

        @Query("DELETE FROM audition WHERE uid = :id")
        suspend fun deletedById(id : Int)

        @Query("select * from AUDITION")
        fun getListAudition() : List<AuditionDB>

        @Query("select * from AUDITION")
        fun getAllFlowAudition() : Flow<List<AuditionDB>>
    }