package com.einfoplanet.demo.domain.restaurantlist

import com.einfoplanet.demo.model.Restaurant
import com.einfoplanet.demo.model.Restaurants
import com.einfoplanet.demo.repository.LoadingStatus
import javax.inject.Inject

class RestaurantListUseCase @Inject constructor(private val restaurantRepository: RestaurantListRepository) {
    fun fetchNearbyRestaurant(lat: Double,
                              lng: Double,
                              onSuccess: (restaurants: Restaurants) -> Unit,
                              onStatus: (status: LoadingStatus) -> Unit) {
        restaurantRepository.fetchNearbyRestaurant(
                lat,
                lng,
                onSuccess,
                onStatus
        )
    }

    fun getRestaurantDetail(restaurantId: String,
                            onSuccess: (restauran: Restaurant) -> Unit,
                            onStatus: (status: LoadingStatus) -> Unit) {
        restaurantRepository.getRestaurantDetail(restaurantId, onSuccess, onStatus)
    }
}