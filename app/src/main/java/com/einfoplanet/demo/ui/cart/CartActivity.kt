package com.einfoplanet.demo.ui.cart

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
        private const val CHANNEL_ID = "12432"
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
        createNotificationChannel()
        createNotification()
        finish()
    }

    override fun buyNowButtonClick() {

    }

    fun createNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_star)
                .setContentTitle("Order Confirmed")
                .setContentText("Arriving on 30/06/2020")
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText("You order is safe with us. Thanks for availing the service." +
                                " Your order will be arriving on 30/06/2020"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1243, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}