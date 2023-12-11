package com.olympia.model.repositories

import com.olympia.model.database.IAbs
import com.olympia.model.entities.DAbs
import kotlinx.coroutines.flow.Flow

class AbsRepository(private val dao: IAbs) : IAbsRepository {
    override suspend fun insertA(abs: DAbs) {
        dao.insertA(abs)
    }

    override suspend fun deleteA(abs: DAbs) {
        dao.deleteA(abs)
    }

    override fun getInfoA(): Flow<List<DAbs>> {
        return dao.getInfoA()
    }

    override suspend fun getRecord(id: Int): DAbs? {
        return dao.getRecord(id)
    }
}