package com.example.cinemaapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CinemaRepo {
    val api : CinemaApi by lazy{
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()

        adapter.create(CinemaApi::class.java)
    }

}