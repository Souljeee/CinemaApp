package com.example.cinemaapp.model

interface Repository {
    fun getCinemaFromServer() : Cinema
    fun getCinemaFromLocalStorage() : List<Cinema>
}