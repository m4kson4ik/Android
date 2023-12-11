package com.example.myrjdapi.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StationDB::class, LikedThread::class], version = 1)

abstract class TrainDateBase : RoomDatabase() {
    abstract fun getDAO() : StationDAO
    abstract fun getDAOLiked() : LikedDAO
}