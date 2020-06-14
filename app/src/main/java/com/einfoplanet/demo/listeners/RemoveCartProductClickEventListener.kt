package com.einfoplanet.demo.listeners

import com.einfoplanet.demo.db.CartProduct

interface RemoveCartProductClickEventListener {
    fun onProductSelected(cartProduct: CartProduct)
}