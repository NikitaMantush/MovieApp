package com.mantushnikita.movieapp.model

data class MovieListResponse(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)