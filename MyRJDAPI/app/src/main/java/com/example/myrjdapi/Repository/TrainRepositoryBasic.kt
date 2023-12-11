package com.example.myrjdapi.Repository

import com.example.myrjdapi.Interface.ITrainRepositoryBasic
import com.example.myrjdapi.Room.LikedThread
import com.example.myrjdapi.Room.StationDB
import com.example.myrjdapi.Room.TrainDateBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrainRepositoryBasic @Inject constructor(db : TrainDateBase) : ITrainRepositoryBasic {
    private val dao = db.getDAO()
    private val daoLiked = db.getDAOLiked()
    override fun getAllFlow():  Flow<List<StationDB>> = dao.getAllFlow()

    override suspend fun insert(item: StationDB) {
        dao.insert(item)
    }

    override suspend fun update(item: StationDB) {
        dao.update(item)
    }

    override suspend fun deleted(item: StationDB) {
        dao.deleted(item)
    }

    override suspend fun insertLikedThread(item: LikedThread) {
        daoLiked.insertLikedThread(item)
    }

    override suspend fun deletedLikedThread(item: LikedThread) {
        daoLiked.deletedLikedThread(item)
    }

    override fun getAllFlowLikedThread(): Flow<List<LikedThread>> = daoLiked.getAllFlowLikedThread()
}