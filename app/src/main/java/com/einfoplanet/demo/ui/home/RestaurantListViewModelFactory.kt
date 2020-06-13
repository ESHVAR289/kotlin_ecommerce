package com.einfoplanet.demo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.domain.restaurantlist.RestaurantListUseCase
import javax.inject.Inject

class RestaurantListViewModelFactory
@Inject constructor(private val restaurantListUseCase: RestaurantListUseCase,
                    private val appExecutors: AppExecutors) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantsViewModel(restaurantListUseCase, appExecutors) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}