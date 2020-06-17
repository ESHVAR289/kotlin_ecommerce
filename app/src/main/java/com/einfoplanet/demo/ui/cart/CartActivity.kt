package com.einfoplanet.demo.ui.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.einfoplanet.demo.R
import com.einfoplanet.demo.ShoppingSampleApp
import com.einfoplanet.demo.listeners.ButtonClickListener
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.ui.cart.CartViewModel.Companion.ESTIMATED_DELIVERY_DATE
import com.einfoplanet.demo.util.inTransaction
import com.einfoplanet.demo.util.viewModelProvider
import javax.inject.Inject

class CartActivity : AppCompatActivity(), ButtonClickListener {

    companion object {
        private const val FRAGMENT_ID = R.id.fragment_container
    }

    @Inject
    lateinit var cartViewModelFactory: CartViewModelFactory
    lateinit var viewmodel: CartViewModel
    private lateinit var currentFragment: MainNavigationFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        ShoppingSampleApp.instance.getApplicationComponent().plusActivityComponent().inject(this)
        viewmodel = viewModelProvider(cartViewModelFactory)
        replaceFragment(CartProductsListingFragment.newInstance())
        initViewModel()
    }

    private fun initViewModel(){

    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment, F : MainNavigationFragment {
        supportFragmentManager.inTransaction {
            currentFragment = fragment
            replace(FRAGMENT_ID, fragment)
        }
    }

    private fun <F> addFragment(fragment: F) where F : Fragment, F : MainNavigationFragment {
        supportFragmentManager.inTransaction {
            currentFragment = fragment
            addToBackStack(FRAGMENT_ID.toString())
            add(FRAGMENT_ID, fragment)
        }
    }

    override fun onBackPressed() {
        if (!currentFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun placeOrderButtonClick() {
        addFragment(OrderDetailsFragment.newInstance(ESTIMATED_DELIVERY_DATE))
    }

    override fun confirmButtonClick(orderId: String) {
        replaceFragment(OrderConfirmationFragment.newInstance(orderId, ESTIMATED_DELIVERY_DATE))
    }

    override fun closeButtonClick() {
        finish()
    }

    override fun buyNowButtonClick() {

    }

}