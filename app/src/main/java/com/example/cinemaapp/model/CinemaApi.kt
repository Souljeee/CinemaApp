package com.example.cinemaapp.model


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CinemaApi {
    @GET("popular")
    fun getCinemas(
        @Query("api_key") token : String,
        @Query("language") language: String
    ): Call<CinemaListDTO>

    @GET("{movie_id}")
    fun getCinema(
        @Path("movie_id") id : Int,
        @Query("api_key") token : String,
        @Query("language") language: String
    ):Call<CinemaDTO>
}