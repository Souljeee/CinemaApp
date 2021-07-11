package com.example.cinemaapp.model

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    const val baseUrl = "https://api.themoviedb.org/3/movie/"

    fun getOkHTTPBuilder():OkHttpClient{
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS)
            .addInterceptor{ chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .method(original.method(),original.body())
                    .build()

                chain.proceed(request)
            }

        return httpClient.build()
    }
}