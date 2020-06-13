package com.einfoplanet.demo.domain.restaurantlist

import com.einfoplanet.demo.model.Restaurants
import com.einfoplanet.demo.repository.LoadingStatus

interface RestaurantListRepository {
    fun fetchNearbyRestaurant(lat: Double,
                              lng: Double,
                              onSuccess: (restaurants: Restaurants) -> Unit,
                              onStatus: (status: LoadingStatus) -> Unit)
}