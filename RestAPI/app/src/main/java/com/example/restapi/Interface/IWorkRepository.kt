package com.example.restapi.Interface

import com.example.restapi.DateBase.CatDB
import kotlinx.coroutines.flow.Flow

interface IWorkRepository {
    fun getAllFlow() : Flow<List<CatDB>>
    suspend fun insert(vararg item: CatDB)
    suspend fun deleted(vararg item : CatDB)
    suspend fun editing(vararg item : CatDB)
}