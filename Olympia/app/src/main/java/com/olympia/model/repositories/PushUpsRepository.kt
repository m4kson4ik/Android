package com.olympia.model.repositories

import com.olympia.model.database.IPushUps
import com.olympia.model.entities.DPushUps
import kotlinx.coroutines.flow.Flow

class PushUpsRepository(private val dao: IPushUps) : IPushUpsRepository {
    override suspend fun insertP(pushUps: DPushUps) {
        dao.insertP(pushUps)
    }

    override suspend fun deleteP(pushUps: DPushUps) {
        dao.deleteP(pushUps)
    }

    override fun getInfoP(): Flow<List<DPushUps>> {
        return dao.getInfoP()
    }

    override suspend fun getRecord(id: Int): DPushUps? {
        return dao.getRecord(id)
    }
}