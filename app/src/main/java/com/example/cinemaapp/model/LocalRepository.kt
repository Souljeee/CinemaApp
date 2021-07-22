package com.example.cinemaapp.model

interface LocalRepository {
    fun getAllHistory(): MutableList<MutableList<Cinema>>
    fun saveEntity(cinema: Cinema)
}