package com.example.myrjdapi.Model

data class Segment(
    val arrival: String,
    val arrival_platform: String,
    val arrival_terminal: Any,
    val departure: String,
    val departure_platform: String,
    val departure_terminal: Any,
    val duration: Double,
    val from: FromX,
    val has_transfers: Boolean,
    val start_date: String,
    val stops: String,
    val thread: Thread,
    val tickets_info: Any,
    val to: String
)