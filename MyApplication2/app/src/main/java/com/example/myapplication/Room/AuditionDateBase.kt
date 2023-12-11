package com.example.myapplication.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AuditionDB::class], version = 1)

abstract class AuditionDateBase : RoomDatabase()
{
        abstract fun auditionDAO() : AuditionBaseDAO
}