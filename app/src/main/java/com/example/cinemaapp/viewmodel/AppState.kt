package com.example.cinemaapp.viewmodel

import com.example.cinemaapp.model.Cinema
import com.example.cinemaapp.model.CinemaDTO


sealed class AppState{
    data class Success(val cinemaData: MutableList<Cinema>) : AppState()
    data class SuccessDetails(val cinema: Cinema) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
