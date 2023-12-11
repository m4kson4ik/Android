package com.olympia.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olympia.model.entities.DAbs
import com.olympia.model.entities.DPushUps
import com.olympia.model.entities.DSquats
import com.olympia.model.entities.DUsers
import javax.inject.Singleton
@Singleton
@Database(entities = [DUsers::class, DPushUps::class, DSquats::class, DAbs::class], version = 2)
abstract class DB : RoomDatabase(){
    abstract val userDao: IUsers
    abstract val pushUpsDao: IPushUps
    abstract val squatsDao: ISquats
    abstract val absDao: IAbs
    companion object {
        const val MAIN_DB = "MainDB.db"
    }
}