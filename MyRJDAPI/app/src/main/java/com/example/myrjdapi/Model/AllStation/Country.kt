package com.example.myrjdapi.Model.AllStation

data class Country (
    val countries : List<CountryItem>
)

data class CountryItem(
    val title: String,
    val regions: List<Region>,
)

data class Region(
    val settlements : List<Settlement>,
    val title : String,
)

data class Settlement (
    val title : String,
    val stations : List<Station>,
    val codes : Codes
)

data class Station (
    val direction : String,
    val station_type : String,
    val title : String,
    val codes : Codes,
    val transport_type : String
)
data class Codes(
    val yandex_code: String
)
