package com.example.cinemaapp.model

interface Repository {
    fun getCinemasFromServer() : List<Cinema>?
    fun getCinemaFromServer(id:Int) : Cinema
    fun getCinemaFromLocalStorage() : List<Cinema>
}