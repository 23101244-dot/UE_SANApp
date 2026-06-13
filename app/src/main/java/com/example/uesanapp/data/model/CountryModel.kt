package com.example.uesanapp.data.model

data class CountryModel (
    val name: String = "",
    val ranking: Int = 0,
    val imageUrl: String = "",
    var isFavorite: Boolean = false
)