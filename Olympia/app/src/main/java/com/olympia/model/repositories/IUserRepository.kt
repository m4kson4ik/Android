package com.olympia.model.repositories

import com.olympia.model.entities.DUsers
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun insertOrUpdateUser(user: DUsers)
    suspend fun deleteUser(user: DUsers)
    fun getUsers(): Flow<List<DUsers>>
    suspend fun getRecord(id: Int?): DUsers?
}