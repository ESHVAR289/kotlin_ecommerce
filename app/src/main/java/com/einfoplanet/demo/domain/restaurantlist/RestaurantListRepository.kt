package com.einfoplanet.demo.domain.restaurantlist

import com.einfoplanet.demo.model.Restaurant
import com.einfoplanet.demo.model.Restaurants
import com.einfoplanet.demo.repository.LoadingStatus

interface RestaurantListRepository {
    fun fetchNearbyRestaurant(lat: Double,
                              lng: Double,
                              onSuccess: (restaurants: Restaurants) -> Unit,
                              onStatus: (status: LoadingStatus) -> Unit)

    fun getRestaurantDetail(restaurantId: String,
                            onSuccess: (restaurant: Restaurant) -> Unit,
                            onStatus: (status: LoadingStatus) -> Unit)
}