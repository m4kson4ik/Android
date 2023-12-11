package com.olympia.model.repositories

import com.olympia.model.entities.DSquats
import kotlinx.coroutines.flow.Flow

interface ISquatsRepository {
    suspend fun insertS(squats: DSquats)
    suspend fun deleteS(squats: DSquats)
    fun getInfoS(): Flow<List<DSquats>>
    suspend fun getRecord(id: Int): DSquats?
}