package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData


// As we need to interact with repostiory which is in data layer. Implementing Dependency Inversion
// Principle we create an interface which concrete respository class in data layer implements.
// This way we remove any dependency on data layer from domain layer
interface CartLocalData {
    fun getAllCartProducts(): LiveData<List<CartProduct>>
    fun removeFromCart(cartProduct: CartProduct)
    fun addProductToCart(cartProduct: CartProduct)
    fun getCartProductCount(): LiveData<Int>
    fun getTotalCartAmount(): LiveData<Int>
}