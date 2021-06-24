package com.example.cinemaapp.model

class RepositoryImpl : Repository {
    override fun getCinemaFromServer() = Cinema()

    override fun getCinemaFromLocalStorage() = getDefaultCinemas()
}