package com.olympia.model.repositories

import com.olympia.model.database.ISquats
import com.olympia.model.entities.DSquats
import kotlinx.coroutines.flow.Flow

class SquatsRepository(private val dao: ISquats) : ISquatsRepository {
    override suspend fun insertS(squats: DSquats) {
        dao.insertS(squats)
    }

    override suspend fun deleteS(squats: DSquats) {
        dao.deleteS(squats)
    }

    override fun getInfoS(): Flow<List<DSquats>> {
        return dao.getInfoS()
    }

    override suspend fun getRecord(id: Int): DSquats? {
        return dao.getRecord(id)
    }
}