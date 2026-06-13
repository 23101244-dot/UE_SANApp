package com.example.uesanapp.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Query("SELECT * FROM favorites_table")
    fun getAllFavorites(): List<CountryEntity> // Ya no necesitas "suspend" si es una consulta simple

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(country: CountryEntity)

    @Delete
    fun deleteFavorite(country: CountryEntity)
}