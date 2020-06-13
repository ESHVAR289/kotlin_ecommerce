package com.einfoplanet.demo.di.component

import com.einfoplanet.demo.di.module.BookDomainModule
import com.einfoplanet.demo.di.module.RestaurantDataModule
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.ui.home.*
import dagger.Subcomponent

@Subcomponent(modules = [RestaurantDataModule::class, BookDomainModule::class])
@FragmentScope
interface FragmentComponent {
    fun inject(booksFragment: BooksFragment)
    fun inject(restaurantsFragment: RestaurantsFragment)
    fun inject(restaurantsActivity: RestaurantsActivity)
}