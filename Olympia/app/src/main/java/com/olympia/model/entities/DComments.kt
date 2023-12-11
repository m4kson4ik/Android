package com.olympia.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DComments(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userName: String,
    val comment: String,
    val date: String)