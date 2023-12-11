package com.example.myappusesensors

import com.example.myappusesensors.DateBase.Plank
import com.example.myappusesensors.DateBase.PlankDateBase
import kotlinx.coroutines.flow.Flow

class PlankRepository(private val dateBase : PlankDateBase) : IPlankRepository {
    private val plankDao = dateBase.plankDao()

    override suspend fun create(vararg plankResult : Plank) {
        plankDao.insert(*plankResult)
    }

    override suspend fun deleted(plank: Plank)
    {
        plankDao.deleted(plank)
    }

    override fun getAllFlow() : Flow<List<Plank>> {
        return plankDao.getAllFlowPlank()
    }
}