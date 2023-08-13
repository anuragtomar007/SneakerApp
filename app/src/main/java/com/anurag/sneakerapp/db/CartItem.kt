package com.anurag.sneakerapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anurag.sneakerapp.data.model.Media

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val id: String,
    val title: String,
    val brand: String,
    val colorway: String,
    val gender: String,
    @Embedded(prefix = "media_") val media: Media,
    val releaseDate: String,
    val retailPrice: Int,
    val styleId: String,
    val shoe: String,
    val name: String,
    val year: Int
)
