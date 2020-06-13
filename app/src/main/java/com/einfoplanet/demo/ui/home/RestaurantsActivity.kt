package com.einfoplanet.demo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.einfoplanet.demo.ShoppingSampleApp
import com.einfoplanet.demo.R
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.inTransaction
import com.einfoplanet.demo.util.viewModelProvider
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
        ShoppingSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        viewModel = viewModelProvider(restaurantViewModelFactory)
        replaceFragment(RestaurantsFragment.newInstance(
                0.0,
                0.0
        ))
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment, F : MainNavigationFragment {
        supportFragmentManager.inTransaction {
            currentFragment = fragment
            replace(FRAGMENT_ID, fragment)
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
