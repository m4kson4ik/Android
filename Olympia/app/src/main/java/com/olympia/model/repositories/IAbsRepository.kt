package com.olympia.model.repositories

import com.olympia.model.entities.DAbs
import kotlinx.coroutines.flow.Flow

interface IAbsRepository {
    suspend fun insertA(abs: DAbs)
    suspend fun deleteA(abs: DAbs)
    fun getInfoA(): Flow<List<DAbs>>
    suspend fun getRecord(id: Int): DAbs?
}