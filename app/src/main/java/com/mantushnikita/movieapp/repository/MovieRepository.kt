package com.mantushnikita.movieapp.repository

import com.mantushnikita.movieapp.network.Api
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val api: Api
) {
    suspend fun getBestMovies() = api.getBestMovies()
    suspend fun getNewtMovies() = api.getNewMovies()
    suspend fun getUpcomingMovies() = api.getUpcomingMovies()
    suspend fun getMovieById(id: Int) = api.getMovieById(id)
    suspend fun searchMovies(query: String) = api.searchMovies(query = query)

}