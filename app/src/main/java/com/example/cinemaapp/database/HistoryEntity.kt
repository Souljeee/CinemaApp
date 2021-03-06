package com.example.cinemaapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String?,
    val poster: String?,
    val releaseDate: String?,
    val rating: String?
)
