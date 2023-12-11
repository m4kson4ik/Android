package com.example.myrjdapi.Model

data class Carrier(
    val address: String,
    val code: Int,
    val codes: Codes,
    val contacts: String,
    val email: String,
    val logo: String,
    val logo_svg: Any,
    val phone: String,
    val title: String,
    val url: String
)