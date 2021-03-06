package com.example.cinemaapp.model

import com.example.cinemaapp.database.HistoryDAO
import com.example.cinemaapp.database.HistoryEntity

class LocalRepositoryImpl(private val localDataSource : HistoryDAO) : LocalRepository {
    override fun getAllHistory(): MutableList<Cinema> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(cinema: Cinema) {
        localDataSource.insert(convertWeatherToEntity(cinema))
    }

    private fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): MutableList<Cinema> {
        return entityList.map {
            Cinema(
                id = it.id.toInt(),
                name = it.name,
                poster = it.poster,
                releaseDate = it.releaseDate,
                rating = it.rating
            )
        } as MutableList<Cinema>
    }

    private fun convertWeatherToEntity(cinema:Cinema): HistoryEntity {
        return HistoryEntity(cinema.id!!.toLong(),cinema.name,cinema.poster,cinema.releaseDate,cinema.rating)
    }
}