package com.einfoplanet.demo.di.component

import com.einfoplanet.demo.di.module.CartModule
import com.einfoplanet.demo.di.module.RestaurantDataModule
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.ui.cart.CartActivity
import com.einfoplanet.demo.ui.home.RestaurantsActivity
import dagger.Subcomponent

@Subcomponent(modules = [RestaurantDataModule::class, CartModule::class])
@FragmentScope
interface ActivityComponent {
    fun inject(restaurantsActivity: RestaurantsActivity)
    fun inject(cartActivity: CartActivity)
}