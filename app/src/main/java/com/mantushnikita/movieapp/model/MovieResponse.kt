package com.mantushnikita.movieapp.model

data class MovieResponse(
    val countries: List<Country>,
    val description: String,
    val enName: Any,
    val genres: List<Genre>,
    val id: Int,
    val movieLength: Int,
    val name: String,
    val persons: List<Person>,
    val poster: Poster,
    val rating: Rating,
    val shortDescription: String,
    val releaseYears: List<ReleaseYear>,
    val year: String?
)