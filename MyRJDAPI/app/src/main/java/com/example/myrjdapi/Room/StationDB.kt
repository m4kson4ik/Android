package com.example.myrjdapi.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myrjdapi.Model.AllStation.Codes

@Entity(tableName = "station")
data class StationDB (
    @ColumnInfo("direction") val direction : String?,
    @ColumnInfo("title") val title : String?,
    @PrimaryKey(false) val codes : String,
    @ColumnInfo("transport_type") val transport_type : String?,
    @ColumnInfo("station_type") val station_type : String?,
    @ColumnInfo("dateCreate") val dateCreate : String?,
    @ColumnInfo("liked") val liked : Boolean? = false,
)

@Entity(tableName = "likedStation")
data class LikedThread(
    @ColumnInfo("direction") val direction : String?,
    @ColumnInfo("stationStart") val stationStart : String?,
    @ColumnInfo("stationEnd") val stationEnd : String?,
    @ColumnInfo("codesStart") val codesStart : String?,
    @ColumnInfo("codesEnd") val codesEnd : String?,
)
{
    @PrimaryKey(true) var uid : Int = 0
}