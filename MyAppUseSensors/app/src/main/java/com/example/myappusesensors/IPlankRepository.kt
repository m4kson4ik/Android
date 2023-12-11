package com.example.myappusesensors

import com.example.myappusesensors.DateBase.Plank
import kotlinx.coroutines.flow.Flow

interface IPlankRepository {
    suspend fun create(vararg plankResult : Plank)
    suspend fun deleted(plank: Plank)
    fun getAllFlow() : Flow<List<Plank>>
}