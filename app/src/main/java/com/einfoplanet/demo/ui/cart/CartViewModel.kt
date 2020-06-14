package com.einfoplanet.demo.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.domain.booklist.CartProductListUseCase
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartProductListUseCase: CartProductListUseCase) : ViewModel() {
    private val _carProductsLiveData: MutableLiveData<List<CartProduct>> by lazy { MutableLiveData<List<CartProduct>>() }
    val cartProductsLiveData: LiveData<List<CartProduct>>
        get() = _carProductsLiveData

    fun getAllCartProducts() {
        _carProductsLiveData.value = cartProductListUseCase.getAllCartProducts()
    }

    fun removeCartProduct(cartProduct: CartProduct) {
        cartProductListUseCase.removeCartProduct(cartProduct)
    }
}