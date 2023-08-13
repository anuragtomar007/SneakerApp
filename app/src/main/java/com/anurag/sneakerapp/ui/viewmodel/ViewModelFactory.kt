package com.anurag.sneakerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anurag.sneakerapp.data.repository.SneakerRepository
import com.anurag.sneakerapp.db.CartDao

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val sneakerRepository: SneakerRepository,
    private val cartDao: CartDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SneakerViewModel::class.java) -> {
                SneakerViewModel(sneakerRepository) as T
            }
            modelClass.isAssignableFrom(SneakerDetailsViewModel::class.java) -> {
                SneakerDetailsViewModel(sneakerRepository) as T
            }
            modelClass.isAssignableFrom(CheckoutViewModel::class.java) -> {
                CheckoutViewModel(sneakerRepository, cartDao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
