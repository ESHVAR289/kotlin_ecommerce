package com.einfoplanet.demo.network

import com.einfoplanet.demo.model.Restaurant
import com.einfoplanet.demo.model.Restaurants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {
    @GET("geocode")
    fun getNearbyRestaurants(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double
    ): Call<Restaurants>

    @GET("restaurant")
    fun getRestaurantDetail(
            @Query("res_id") restaurantId: String
    ): Call<Restaurant>

    @GET("search")
    fun searchRestaurant(
            @Query("entity_type") entityType: String = "city",
            @Query("lat") latitude: Double,
            @Query("lon") longitude: Double,
            @Query("category") category: String = "",
            @Query("sort") sort: String = "rating"
    ): Call<Restaurants>
}