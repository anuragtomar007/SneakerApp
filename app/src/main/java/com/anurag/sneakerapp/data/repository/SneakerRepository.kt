package com.anurag.sneakerapp.data.repository

import com.anurag.sneakerapp.data.dummySneakers
import com.anurag.sneakerapp.data.model.Sneaker

class SneakerRepository {

    fun fetchDummySneakerById(sneakerId: String): Sneaker {
        return dummySneakers.find { it.id == sneakerId }!!
    }

    fun fetchDummySneakersData() : List<Sneaker>{
        return dummySneakers
    }
}
