package com.einfoplanet.demo.domain.cartlist

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.db.CartProduct

// As we need to interact with repostiory which is in data layer. Implementing Dependency Inversion
// Principle we create an interface which concrete respository class in data layer implements.
// This way we remove any dependency on data layer from domain layer
interface CartListRepository {
    fun getAllCartProducts(): LiveData<List<CartProduct>>
    fun removeFromCart(cartProduct: CartProduct)
    fun addProductToCart(cartProduct: CartProduct)
    fun getCartProductsCount(): LiveData<Int>
    fun getTotalCartAmount(): LiveData<Int>
}