package com.olympia.model.repositories

import com.olympia.model.entities.DPushUps
import kotlinx.coroutines.flow.Flow

interface IPushUpsRepository {
    suspend fun insertP(pushUps: DPushUps)
    suspend fun deleteP(pushUps: DPushUps)
    fun getInfoP(): Flow<List<DPushUps>>
    suspend fun getRecord(id: Int): DPushUps?
}