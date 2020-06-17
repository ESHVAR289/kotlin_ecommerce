package com.einfoplanet.demo.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.domain.cartlist.CartProductListUseCase
import com.einfoplanet.demo.listeners.RemoveCartProductClickEventListener
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartProductListUseCase: CartProductListUseCase) : ViewModel(), RemoveCartProductClickEventListener {
    companion object{
        const val ESTIMATED_DELIVERY_DATE = "30/06/2020"
    }
    val cartProductsLiveData: LiveData<List<CartProduct>> = cartProductListUseCase.getAllCartProducts()
    val cartProductsCount: LiveData<Int> = cartProductListUseCase.getCartProductsCount()
    val cartTotalAmount: LiveData<Int> = cartProductListUseCase.getTotalCartAmount()

    private val _productSelectedToRemove: MutableLiveData<CartProduct> by lazy { MutableLiveData<CartProduct>() }
    val productSelectedToRemove: LiveData<CartProduct>
        get() = _productSelectedToRemove

    fun removeCartProduct(cartProduct: CartProduct) {
        cartProductListUseCase.removeCartProduct(cartProduct)
    }

    override fun onProductSelected(cartProduct: CartProduct) {
        _productSelectedToRemove.value = cartProduct
    }

    fun clearCart(){
        cartProductListUseCase.clearCart()
    }

}