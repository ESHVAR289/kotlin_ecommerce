package com.einfoplanet.demo.ui.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.einfoplanet.demo.R
import com.einfoplanet.demo.ShoppingSampleApp

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        ShoppingSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
    }
}