package com.example.myapplication.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audition")
data class AuditionDB(
    @ColumnInfo(name = "startDate") var startDate : String,
    @ColumnInfo(name = "endDate") var endDate : String,
    @ColumnInfo(name = "numberAudition") var numberAudition : Int,
    @ColumnInfo(name = "name") var name : String,
    var uri : String? = null,
    var numberImage : Int? = 0
){
    @PrimaryKey(autoGenerate = true) var uid : Int = 0
}