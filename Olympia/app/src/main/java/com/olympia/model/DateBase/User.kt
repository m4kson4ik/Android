package com.olympia.model.DateBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Singleton

@Singleton
@Entity(tableName = "users")
data class Users(
    @ColumnInfo(name ="user_name") var userName : String,
    @ColumnInfo(name ="gender") var gender : String,
    @ColumnInfo(name ="birthday") var birthday : String,
    @ColumnInfo(name ="email") var email : String,
    @ColumnInfo(name ="password") var password : String,
    @ColumnInfo(name ="autoSign") var autoSign : Boolean
    ){
    @PrimaryKey(autoGenerate = true) var idUser : Int = 0
}