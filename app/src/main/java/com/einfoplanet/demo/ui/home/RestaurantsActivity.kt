package com.einfoplanet.demo.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.einfoplanet.demo.R
import com.einfoplanet.demo.ShoppingSampleApp
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.ui.cart.CartActivity
import com.einfoplanet.demo.util.inTransaction
import com.einfoplanet.demo.util.viewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class RestaurantsActivity : AppCompatActivity() {
    companion object {
        private const val FRAGMENT_ID = R.id.fragment_container
    }

    @Inject
    lateinit var restaurantViewModelFactory: RestaurantListViewModelFactory
    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var currentFragment: MainNavigationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ShoppingSampleApp.instance.getApplicationComponent().plusActivityComponent().inject(this)
        viewModel = viewModelProvider(restaurantViewModelFactory)
        replaceFragment(RestaurantsFragment.newInstance(
                0.0,
                0.0
        ))

        iv_cart.setOnClickListener {
            viewModel.cartProductsLiveData.value?.let {
                if (it.isNotEmpty()) {
                    startActivity(Intent(this@RestaurantsActivity, CartActivity::class.java))
                } else {
                    Toast.makeText(this, "Your cart is empty.", Toast.LENGTH_SHORT).show()
                }

            } ?: Toast.makeText(this, "Your cart is empty.", Toast.LENGTH_SHORT).show()

        }

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.clickedRestaurantData.observe(this, Observer {
            addFragment(
                    RestaurantDetailFragment.newInstance(
                            it.id
                    )
            )
        })

        //This observer is used to check the count of added cart products
        viewModel.cartProductsLiveData.observe(this, Observer {
            it?.let {
                if (it.isNotEmpty()) {
                    tv_cart_items_count.visibility = View.VISIBLE
                    tv_cart_items_count.text = it.size.toString()
                } else {
                    tv_cart_items_count.visibility = View.GONE
                }
            }
        })

        viewModel.itemsIntoCart.observe(this, Observer {
            Log.e("ItemsIntoCart", " ITEMS $it")
        })
        viewModel.cartProductsLiveData
        viewModel.itemsIntoCart
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

    override fun onUserInteraction() {
        super.onUserInteraction()
        currentFragment.onUserInteraction()
    }

}
