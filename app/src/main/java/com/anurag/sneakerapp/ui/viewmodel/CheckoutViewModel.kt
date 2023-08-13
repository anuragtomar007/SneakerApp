package com.anurag.sneakerapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anurag.sneakerapp.data.model.Sneaker
import com.anurag.sneakerapp.data.repository.SneakerRepository
import com.anurag.sneakerapp.db.CartDao
import com.anurag.sneakerapp.db.CartItem
import kotlinx.coroutines.launch

class CheckoutViewModel(private val sneakerRepository: SneakerRepository, private val cartDao: CartDao) : ViewModel() {
    private val _cart = MutableLiveData<List<Sneaker>>()

    val cartItemLiveData: LiveData<List<CartItem>> = cartDao.getAllCartItems()

    private val _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String> = _totalPrice

    private val _totalPriceWithTax = MutableLiveData<String>()
    val totalPriceWithTax: LiveData<String> = _totalPriceWithTax

    init {
        updateTotalPrice()
    }

    fun fetchSneakerById(sneakerId: String): Sneaker {
        return sneakerRepository.fetchDummySneakerById(sneakerId)
    }

    suspend fun addToCart(sneaker: Sneaker) {
        val currentCart = _cart.value.orEmpty()
        val updatedCart = currentCart + sneaker
        _cart.value = updatedCart

        val cartItem = CartItem(
            sneaker.id,
            sneaker.title,
            sneaker.brand,
            sneaker.colorway,
            sneaker.gender,
            sneaker.media,
            sneaker.releaseDate,
            sneaker.retailPrice,
            sneaker.styleId,
            sneaker.shoe,
            sneaker.name,
            sneaker.year
        )

        cartDao.insertCartItem(cartItem)
        updateTotalPrice()
    }

    fun removeFromCart(sneakerId: String) {
        val currentCart = _cart.value.orEmpty()
        val updatedCart = currentCart.filter { it.id != sneakerId }
        _cart.value = updatedCart
        updateTotalPrice()

        viewModelScope.launch {
            cartDao.deleteCartItemById(sneakerId)
        }
    }


    fun updateTotalPrice() {
        val cartItems = cartItemLiveData.value
        val total = cartItems?.sumOf { it.retailPrice } ?: 0
        _totalPrice.value = String.format("$%d", total)

        val totalWithTax = total + 40
        _totalPriceWithTax.value = String.format("$%d", totalWithTax)
    }

    fun fetchAllSneakers(): LiveData<List<CartItem>> {
        return cartDao.getAllCartItems()
    }
}
