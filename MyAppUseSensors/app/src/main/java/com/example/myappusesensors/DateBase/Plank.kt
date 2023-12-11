package com.example.myappusesensors.DateBase

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "plank")
data class Plank (
    @ColumnInfo() var startResult : String,
    @ColumnInfo() var endResult : String
){
    @PrimaryKey(autoGenerate = true) var uid : Int = 0
}

@Dao
interface PlankDAO {
    @Insert
    suspend fun insert(vararg plank : Plank)

    @Delete
    suspend fun deleted(plank: Plank)

    @Query("select * from plank")
    fun getAllFlowPlank() : Flow<List<Plank>>
}


@Database(entities = [Plank::class], version = 1)
abstract class PlankDateBase : RoomDatabase()
{
    abstract fun plankDao() : PlankDAO
}