package com.example.myrjdapi.Model

enum class Status
{
    Waiting,
    OK,
    Error,
    None,
}


data class Flights(
    val type : String,
    val title : String,
    val code : String,
    val station_type : String
)

