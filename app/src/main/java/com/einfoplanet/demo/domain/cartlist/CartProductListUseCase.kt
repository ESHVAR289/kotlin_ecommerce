package com.einfoplanet.demo.domain.cartlist

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.db.CartProduct
import javax.inject.Inject

class CartProductListUseCase @Inject constructor(private val repository: CartListRepository) {

    fun getAllCartProducts(): LiveData<List<CartProduct>> {
        return repository.getAllCartProducts()
    }

    fun removeCartProduct(cartProduct: CartProduct) {
        return repository.removeFromCart(cartProduct)
    }

    fun addProductToCart(cartProduct: CartProduct) {
        return repository.addProductToCart(cartProduct)
    }

    fun getCartProductsCount(): LiveData<Int> {
        return repository.getCartProductsCount()
    }

    fun getTotalCartAmount(): LiveData<Int> {
        return repository.getTotalCartAmount()
    }

    fun clearCart() {
        repository.clearCart()
    }
}