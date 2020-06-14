package com.einfoplanet.demo.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.einfoplanet.demo.domain.booklist.CartProductListUseCase
import javax.inject.Inject

class CartViewModelFactory
@Inject constructor(private val cartProductListUseCase: CartProductListUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartProductListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}