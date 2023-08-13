package com.anurag.sneakerapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anurag.sneakerapp.data.dummySneakers
import com.anurag.sneakerapp.data.model.Sneaker
import com.anurag.sneakerapp.data.repository.SneakerRepository

class SneakerViewModel(private val sneakerRepository: SneakerRepository) : ViewModel() {

    private val _sneakers = MutableLiveData<List<Sneaker>>()
    val sneakers: LiveData<List<Sneaker>> = _sneakers

    init {
        _sneakers.value = dummySneakers
    }

    fun updateSneakersFiltered(filteredSneakers: List<Sneaker>) {
        _sneakers.value = filteredSneakers
    }

    fun getDummyData() : List<Sneaker>{
        return sneakerRepository.fetchDummySneakersData()
    }
}
