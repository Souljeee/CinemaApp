package com.example.cinemaapp.model

interface LocalRepository {
    fun getAllHistory(): MutableList<Cinema>
    fun saveEntity(cinema: Cinema)
}