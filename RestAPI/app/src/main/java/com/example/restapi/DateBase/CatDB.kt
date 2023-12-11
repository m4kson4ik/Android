package com.example.restapi.DateBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class CatDB (
    @ColumnInfo(name = "text") var text : String,
    @ColumnInfo(name = "type") var type : String,
    @ColumnInfo(name = "updatedAt") var updatedAt : String,
)
{
    @PrimaryKey(autoGenerate = true) var uid : Int = 0
}