package com.olympia.model.DateBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comments(
    @ColumnInfo(name ="idUser") var idUser : Int,
    @ColumnInfo(name ="comments") var comments : String,
    @ColumnInfo(name ="date") var text : String,
    ){
    @PrimaryKey(autoGenerate = true) var idComment : Int = 0
}