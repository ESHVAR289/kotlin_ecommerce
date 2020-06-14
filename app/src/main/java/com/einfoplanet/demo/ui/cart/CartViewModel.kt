package com.einfoplanet.demo.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.domain.cartlist.CartProductListUseCase
import com.einfoplanet.demo.listeners.RemoveCartProductClickEventListener
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartProductListUseCase: CartProductListUseCase) : ViewModel(), RemoveCartProductClickEventListener {
    private val _carProductsLiveData: MutableLiveData<List<CartProduct>> by lazy { MutableLiveData<List<CartProduct>>() }
    val cartProductsLiveData: LiveData<List<CartProduct>> = cartProductListUseCase.getAllCartProducts()
    val cartProductsCount: LiveData<Int> = cartProductListUseCase.getCartProductsCount()

    fun removeCartProduct(cartProduct: CartProduct) {
        cartProductListUseCase.removeCartProduct(cartProduct)
    }

    override fun onProductSelected(cartProduct: CartProduct) {

    }

}