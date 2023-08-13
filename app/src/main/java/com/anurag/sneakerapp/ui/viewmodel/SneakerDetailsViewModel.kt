package com.anurag.sneakerapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anurag.sneakerapp.data.model.Sneaker
import com.anurag.sneakerapp.data.repository.SneakerRepository
import kotlinx.coroutines.launch

class SneakerDetailsViewModel(private val sneakerRepository: SneakerRepository) : ViewModel() {

    private val _cart = MutableLiveData<List<Sneaker>>()

    private val _sneakerDetails = MutableLiveData<Sneaker>()
    val sneakerDetails: LiveData<Sneaker> = _sneakerDetails

    fun fetchSneakerDetails(sneakerId: String) {
        viewModelScope.launch {
            try {
                val sneaker = sneakerRepository.fetchDummySneakerById(sneakerId)
                _sneakerDetails.postValue(sneaker)
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }

    fun addToCart(sneaker: Sneaker) {
        val currentCart = _cart.value ?: emptyList()
        val updatedCart = currentCart + sneaker
        _cart.value = updatedCart
    }
}
