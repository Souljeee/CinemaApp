package com.example.cinemaapp.model

interface Repository {
    fun getCinemas() : MutableList<MutableList<Cinema>>?
    fun getPopularCinemasFromServer() : List<Cinema>?
    fun getTopRatedCinemasFromServer() : List<Cinema>?
    fun getUpcomingCinemasFromServer() : List<Cinema>?
    fun getNowPlayingCinemasFromServer() : List<Cinema>?
    fun getCinemaFromServer(id:Int) : Cinema

}