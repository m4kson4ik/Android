package com.olympia.model.DateBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Posts(
    @ColumnInfo(name ="comments") var idUser : Int,
    @ColumnInfo(name ="text") var text : String,
    @ColumnInfo(name ="url") var userName : String,
    ){
    @PrimaryKey(autoGenerate = true) var idPost : Int = 0
}