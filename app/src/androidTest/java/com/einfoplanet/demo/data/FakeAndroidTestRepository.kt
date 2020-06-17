package com.einfoplanet.demo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.domain.cartlist.CartListRepository

class FakeAndroidTestRepository : CartListRepository {

    private val _allCartProductsData: MutableLiveData<List<CartProduct>> = MutableLiveData(emptyList())
    private val allCartProductsData: LiveData<List<CartProduct>>
        get() = _allCartProductsData

    private val _productsSize: MutableLiveData<Int> = MutableLiveData()
    private val productSize: LiveData<Int>
        get() = _productsSize

    private val _allProductsTotal: MutableLiveData<Int> = MutableLiveData()
    private val allProductsTotal: LiveData<Int>
        get() = _allProductsTotal

    override fun getAllCartProducts(): LiveData<List<CartProduct>> {
        return allCartProductsData
    }

    override fun removeFromCart(cartProduct: CartProduct) {
        _allCartProductsData.postValue(_allCartProductsData.value!!.minus(cartProduct))
    }

    override fun addProductToCart(cartProduct: CartProduct) {
        _allCartProductsData.postValue(_allCartProductsData.value!!.plus(cartProduct))
    }

    override fun getCartProductsCount(): LiveData<Int> {
        return productSize
    }

    override fun getTotalCartAmount(): LiveData<Int> {
        return allProductsTotal
    }

    override fun clearCart() {
        _allCartProductsData.value = emptyList()
    }
}