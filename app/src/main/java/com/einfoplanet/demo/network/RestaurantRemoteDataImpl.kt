package com.einfoplanet.demo.network

import com.einfoplanet.demo.domain.restaurantlist.RestaurantListRepository
import com.einfoplanet.demo.model.Restaurants
import com.einfoplanet.demo.repository.ErrorCode
import com.einfoplanet.demo.repository.LoadingStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RestaurantRemoteDataImpl @Inject constructor(private val restaurantService: RestaurantService) :
        RestaurantListRepository {
    override fun fetchNearbyRestaurant(lat: Double,
                                       lng: Double,
                                       onSuccess: (restaurants: Restaurants) -> Unit,
                                       onStatus: (status: LoadingStatus) -> Unit) {
        val call = restaurantService.getNearbyRestaurants(lat, lng)
        call.enqueue(object : Callback<Restaurants> {
            override fun onResponse(call: Call<Restaurants>?, response: Response<Restaurants>?) {
                if (response != null) {
                    if (response.body() == null) {
                        onStatus(LoadingStatus.error(ErrorCode.NO_DATA))
                    } else {
                        response.body()?.let {
                            onSuccess(it)
                        }
                        onStatus(LoadingStatus.success())
                    }
                }
            }

            override fun onFailure(call: Call<Restaurants>?, t: Throwable?) {
                if (t is IOException) {
                    onStatus(
                            LoadingStatus.error(
                                    ErrorCode.NETWORK_ERROR, t.message))
                } else {
                    onStatus(
                            LoadingStatus.error(
                                    ErrorCode.UNKNOWN_ERROR, null))
                }
            }
        })
    }
}