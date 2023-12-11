package com.example.myrjdapi.Model

data class Train(
    val interval_segments: List<Any>,
    val pagination: Pagination,
    val search: Search,
    val segments: List<Segment>
)