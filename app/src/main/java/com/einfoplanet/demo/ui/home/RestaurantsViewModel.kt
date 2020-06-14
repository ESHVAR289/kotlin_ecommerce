package com.einfoplanet.demo.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.domain.restaurantlist.RestaurantListUseCase
import com.einfoplanet.demo.listeners.RestaurantClickedEventListener
import com.einfoplanet.demo.model.Restaurant
import com.einfoplanet.demo.model.Restaurants
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(private val restaurantListUseCase: RestaurantListUseCase,
                                               private val appExecutors: AppExecutors) : ViewModel(),
        RestaurantClickedEventListener {
    companion object {
    }

    private val _restaurantsLiveData: MutableLiveData<List<Restaurants.NearbyRestaurant>> by lazy { MutableLiveData<List<Restaurants.NearbyRestaurant>>() }
    val restaurantsLiveData: MutableLiveData<List<Restaurants.NearbyRestaurant>>
        get() = _restaurantsLiveData

    private val _singleRestaurantData: MutableLiveData<Restaurant> by lazy { MutableLiveData<Restaurant>() }
    val singleRestaurantData: MutableLiveData<Restaurant>
        get() = _singleRestaurantData

    private val _popularityLiveData: MutableLiveData<Restaurants.Popularity> by lazy { MutableLiveData<Restaurants.Popularity>() }
    val popularityLiveData: MutableLiveData<Restaurants.Popularity>
        get() = _popularityLiveData

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val isLoading: MutableLiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MediatorLiveData<String>()
    val errorMessage: MutableLiveData<String>
        get() = _errorMessage

    private val _clickedRestaurantData: MutableLiveData<Restaurants.NearbyRestaurant.Restaurant> by lazy { MutableLiveData<Restaurants.NearbyRestaurant.Restaurant>() }
    val clickedRestaurantData: MutableLiveData<Restaurants.NearbyRestaurant.Restaurant>
        get() = _clickedRestaurantData

    var currentLatitude: Double = 0.0
    var currentLongitude: Double = 0.0
    var selectedResId: String = ""
    var updatedList: List<Restaurants.NearbyRestaurant> = emptyList()

    fun getNearbyRestaurants(currentLatitude: Double, currentLongitude: Double) {
        restaurantListUseCase.fetchNearbyRestaurant(19.126582, 72.865980, {
            _restaurantsLiveData.value = it.nearByRestaurant
        }, {
            _errorMessage.value = it.message
        })
    }

    fun getRestaurantDetail(restaurantId: String) {
        restaurantListUseCase.getRestaurantDetail(
                restaurantId,
                {
                    _singleRestaurantData.value = it
                },
                {
                    _errorMessage.value = it.message
                }
        )
    }

    override fun onRestaurantSelected(restaurant: Restaurants.NearbyRestaurant.Restaurant) {
        _clickedRestaurantData.value = restaurant
    }
}