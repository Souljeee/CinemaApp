package com.example.cinemaapp.viewmodel

import com.example.cinemaapp.model.Cinema


sealed class AppState{
    data class Success(val cinemaData: List<Cinema>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
