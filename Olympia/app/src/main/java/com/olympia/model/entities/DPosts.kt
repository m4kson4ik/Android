package com.olympia.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DPosts(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val header: String,
    val description: String,
    val date: String,
    val photo: Int)