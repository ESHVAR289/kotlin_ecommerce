package com.einfoplanet.demo.repository

import com.einfoplanet.demo.db.CartProduct

// This interface is in accordance to Dependency Inversion Principle by separating the higher
// repository class from lower database class.
interface CartProductsLocalData {
    fun getAllCartProducts(): List<CartProduct>
    fun removeFromCart(cartProduct: CartProduct)
    fun addProductToCart(cartProduct: CartProduct)
}