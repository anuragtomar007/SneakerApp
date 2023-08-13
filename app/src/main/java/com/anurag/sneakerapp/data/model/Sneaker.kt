package com.anurag.sneakerapp.data.model

data class Sneaker(
    val id: String,
    val brand: String,
    val colorway: String,
    val gender: String,
    val media: Media,
    val releaseDate: String,
    val retailPrice: Int,
    val styleId: String,
    val shoe: String,
    val name: String,
    val title: String,
    val year: Int
)
