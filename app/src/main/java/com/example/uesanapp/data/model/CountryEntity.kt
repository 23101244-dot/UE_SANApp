package com.example.uesanapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class CountryEntity(
    @PrimaryKey val name: String,
    val ranking: Int,
    val imageUrl: String,
    // Este campo es opcional, pero ayuda a saber si está sincronizado
    val isSynced: Boolean = true
)