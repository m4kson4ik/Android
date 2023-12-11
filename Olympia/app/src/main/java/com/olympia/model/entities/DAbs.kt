package com.olympia.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DAbs(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstRep: Int,
    val secondRep: Int,
    val thirdRep: Int,
    val fourthRep: Int,
    val fifthRep: Int,
    val staminaTest: Int)