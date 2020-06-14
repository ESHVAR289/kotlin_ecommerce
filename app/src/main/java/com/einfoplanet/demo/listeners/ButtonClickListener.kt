package com.einfoplanet.demo.listeners

interface ButtonClickListener {
    fun placeOrderButtonClick()
    fun confirmButtonClick(orderId: String)
    fun okButtonClick()
}