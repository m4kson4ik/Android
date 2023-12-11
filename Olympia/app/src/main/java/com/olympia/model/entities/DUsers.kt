package com.olympia.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class DUsers(
    val userName: String,
    val password: String,
    val gender: String,
    val dateOfBirth: String,
    val email: String,
    val registerDate: String = LocalDate.now().toString())
{
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}