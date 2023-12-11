package com.example.restapi.DateBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CatDB::class], version = 1)

abstract class CatDateBase : RoomDatabase() {
    abstract fun getCatDAO() : CatDAO
}