package com.example.myrjdapi.Model.NearestСity

data class NearestCity(
    val code: String,
    val distance: Double,
    val lat: Double,
    val lng: Double,
    val popular_title: String,
    val short_title: String,
    val title: String,
    val type: String
)