package com.mantushnikita.movieapp.network

import com.mantushnikita.movieapp.model.MovieListResponse
import com.mantushnikita.movieapp.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("movie")
    suspend fun getBestMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
        @Query("rating.imdb") ratingImdb: String = "7-10",
        @Query("votes.imdb") voteImdb: String = "1000000-6666666",
        @Query("persons.enProfession") personProfession: String = "actor",
        @Query("selectFields") selectFields: List<String> = listOf(
            "id", "name", "enName", "names", "description", "shortDescription",
            "type", "releaseYears", "rating", "movieLength", "genres",
            "countries", "poster", "persons"
        ),
        @Query("notNullFields") notNullFields: List<String> = listOf(
            "name", "poster.url", "description", "rating.imdb"
        )
    ): Response<MovieListResponse>

    @GET("movie")
    suspend fun getNewMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
        @Query("year") year: String = "2020",
        @Query("persons.enProfession") personProfession: String = "actor",
        @Query("selectFields") selectFields: List<String> = listOf(
            "id", "name", "enName", "names", "description", "shortDescription",
            "type", "releaseYears", "rating","year","movieLength", "genres",
            "countries", "poster", "persons"
        ),
        @Query("notNullFields") notNullFields: List<String> = listOf(
            "name", "poster.url", "description", "rating.imdb"
        )
    ): Response<MovieListResponse>

    @GET("movie")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15,
        @Query("type") type: String? = null,
        @Query("status") status: String = "announced",
        @Query("persons.enProfession") personProfession: String = "actor",
        @Query("selectFields") selectFields: List<String> = listOf(
            "id", "name", "enName", "names", "description", "shortDescription",
            "type", "releaseYears", "rating", "movieLength", "genres",
            "countries", "poster", "persons"
        ),
        @Query("notNullFields") notNullFields: List<String> = listOf(
            "name", "poster.url", "description", "rating.imdb"
        )
    ): Response<MovieListResponse>

    @GET("movie/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Response<MovieResponse>

    @GET("movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("query") query: String
    ): Response<MovieListResponse>
}
