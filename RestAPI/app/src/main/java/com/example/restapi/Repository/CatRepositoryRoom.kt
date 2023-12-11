package com.example.restapi.Repository

import com.example.restapi.DateBase.CatDB
import com.example.restapi.DateBase.CatDateBase
import com.example.restapi.Interface.IWorkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatRepositoryRoom @Inject constructor(db : CatDateBase) : IWorkRepository {
    private val dbDao = db.getCatDAO()
    override fun getAllFlow(): Flow<List<CatDB>> = dbDao.getAllFlow()

    override suspend fun insert(vararg item: CatDB) {
        dbDao.insert(*item)
    }

    override suspend fun deleted(vararg item: CatDB) {
        dbDao.deleted()
    }

    override suspend fun editing(vararg item: CatDB) {
        dbDao.insert(*item)
    }
}