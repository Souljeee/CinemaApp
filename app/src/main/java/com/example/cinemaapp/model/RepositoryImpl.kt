package com.example.cinemaapp.model

class RepositoryImpl : Repository {
    override fun getCinemaFromServer(): Cinema {
        return Cinema()
    }

    override fun getCinemaFromLocalStorage(): List<Cinema> {
        return getDefaultCinemas();
    }
}