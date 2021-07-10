package com.example.cinemaapp.database

import androidx.room.*

@Dao
interface HistoryDAO {
    @Query("SELECT * FROM HistoryEntity")
    fun all():List<HistoryEntity>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)
}