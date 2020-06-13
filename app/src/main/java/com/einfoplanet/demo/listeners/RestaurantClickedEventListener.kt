package com.einfoplanet.demo.listeners

import com.einfoplanet.demo.model.Restaurants

interface RestaurantClickedEventListener {
    fun onRestaurantSelected(restaurant: Restaurants.NearbyRestaurant.Restaurant)
}