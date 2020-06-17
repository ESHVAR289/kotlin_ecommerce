package com.einfoplanet.demo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.domain.cartlist.CartProductListUseCase
import com.einfoplanet.demo.domain.restaurantlist.RestaurantListUseCase
import com.einfoplanet.demo.listeners.RestaurantClickedEventListener
import com.einfoplanet.demo.model.Restaurant
import com.einfoplanet.demo.model.Restaurants
import javax.inject.Inject

class RestaurantsViewModel @Inject constructor(private val restaurantListUseCase: RestaurantListUseCase,
                                               private val cartProductListUseCase: CartProductListUseCase) : ViewModel(),
        RestaurantClickedEventListener {
    companion object {
    }

    private val _restaurantsLiveData: MutableLiveData<List<Restaurants.NearbyRestaurant>> by lazy { MutableLiveData<List<Restaurants.NearbyRestaurant>>() }
    val restaurantsLiveData: MutableLiveData<List<Restaurants.NearbyRestaurant>>
        get() = _restaurantsLiveData

    private val _singleRestaurantData: MutableLiveData<Restaurant> by lazy { MutableLiveData<Restaurant>() }
    val singleRestaurantData: MutableLiveData<Restaurant>
        get() = _singleRestaurantData

    val itemsIntoCart: LiveData<Int> = cartProductListUseCase.getCartProductsCount()
    val cartProductsLiveData: LiveData<List<CartProduct>> = cartProductListUseCase.getAllCartProducts()

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
        _isLoading.value = true
        restaurantListUseCase.fetchNearbyRestaurant(19.126582, 72.865980, {
            _isLoading.value = false
            _restaurantsLiveData.value = it.nearByRestaurant
        }, {
            _isLoading.value = false
            _errorMessage.value = it.message
        })
    }

    fun getRestaurantDetail(restaurantId: String) {
        _isLoading.value = true
        restaurantListUseCase.getRestaurantDetail(
                restaurantId,
                {
                    _isLoading.value = false
                    _singleRestaurantData.value = it
                },
                {
                    _isLoading.value = false
                    _errorMessage.value = it.message
                }
        )
    }

    fun addProductToCart(restaurant: Restaurant, quantity: String) {
        cartProductListUseCase.addProductToCart(CartProduct(
                restaurantId = restaurant.id,
                restaurantName = restaurant.name,
                url = restaurant.url,
                quantity = quantity.toInt(),
                averageCostForTwo = restaurant.averageCostForTwo,
                thumb = restaurant.thumb
        ))
    }

    override fun onRestaurantSelected(restaurant: Restaurants.NearbyRestaurant.Restaurant) {
        _clickedRestaurantData.value = restaurant
    }
}