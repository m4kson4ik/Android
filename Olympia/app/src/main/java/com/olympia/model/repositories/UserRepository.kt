package com.olympia.model.repositories

import com.olympia.model.database.IUsers
import com.olympia.model.entities.DUsers
import kotlinx.coroutines.flow.Flow

class UserRepository(private val dao: IUsers) : IUserRepository {
    override suspend fun insertOrUpdateUser(user: DUsers) {
        dao.insertOrUpdateUser(user)
    }

    override suspend fun deleteUser(user: DUsers) {
        dao.deleteUser(user)
    }

    override fun getUsers(): Flow<List<DUsers>> {
        return dao.getUsers()
    }

    override suspend fun getRecord(id: Int?): DUsers? {
        return dao.getRecord(id)
    }
}