package com.einfoplanet.demo.repository

import com.einfoplanet.demo.model.Restaurants

// This interface is in accordance to Dependency Inversion Principle by separating the higher
// repository class from lower network class.
interface RestaurantRemoteData {
    fun fetchNearbyRestaurant(lat: Double,
                              lng: Double,
                              onSuccess: (restaurants: Restaurants) -> Unit,
                              onStatus: (status: LoadingStatus) -> Unit)
}