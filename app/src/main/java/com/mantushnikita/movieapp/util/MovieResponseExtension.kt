package com.mantushnikita.movieapp.util

import com.mantushnikita.movieapp.model.Doc
import com.mantushnikita.movieapp.model.MovieResponse


fun MovieResponse.toDoc(): Doc {
    return Doc(
        countries,
        description,
        enName,
        genres,
        id,
        movieLength,
        name,
        persons,
        poster,
        rating,
        shortDescription,
        releaseYears,
        year
    )
}