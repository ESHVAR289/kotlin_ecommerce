package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.AppExecutors
import javax.inject.Inject

class CartLocalDataImpl @Inject constructor(private val appExecutors: AppExecutors,
                                            private val cartDao: CartDao) : CartLocalData {
    override fun getAllCartProducts(): LiveData<List<CartProduct>> {
        return cartDao.getAllCartProducts()
    }

    override fun removeFromCart(cartProduct: CartProduct) {
        appExecutors.diskIO().execute {
            cartDao.deleteProductFromCart(cartProduct.id)
        }
    }

    override fun addProductToCart(cartProduct: CartProduct) {
        appExecutors.diskIO().execute {
            cartDao.insertProduct(cartProduct)
        }
    }

    override fun getCartProductCount(): LiveData<Int> {
        return cartDao.getCartProductCount()
    }

    override fun getTotalCartAmount(): LiveData<Int> {
        return cartDao.getAllProductTotalPrice()
    }

}