package com.einfoplanet.demo.repository

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.db.CartLocalDataImpl
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.domain.cartlist.CartListRepository
import javax.inject.Inject


class CartListRepositoryImpl @Inject constructor(private val localData: CartLocalDataImpl)
    : CartListRepository {

    override fun getAllCartProducts(): LiveData<List<CartProduct>> =
            localData.getAllCartProducts()


    override fun removeFromCart(cartProduct: CartProduct) =
            localData.removeFromCart(cartProduct)


    override fun addProductToCart(cartProduct: CartProduct) =
            localData.addProductToCart(cartProduct)

    override fun getCartProductsCount(): LiveData<Int> = localData.getCartProductCount()

    override fun getTotalCartAmount(): LiveData<Int> = localData.getTotalCartAmount()

    override fun clearCart() = localData.clearCart()
}
