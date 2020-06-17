package com.einfoplanet.demo.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.domain.cartlist.CartProductListUseCase
import com.einfoplanet.demo.ui.cart.CartViewModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CartViewModelTest {
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var cartViewModel: CartViewModel

    // Use a fake repository to be injected into the viewmodel
    private lateinit var fakeRepository: FakeTestRepository


    @Before
    fun setupStatisticsViewModel() {
        // We initialise the repository with no tasks
        fakeRepository = FakeTestRepository()
        val cartProductListUseCase = CartProductListUseCase(fakeRepository)
        cartViewModel = CartViewModel(cartProductListUseCase)
    }

    @Test
    fun loadAllTasks_AddProduct_returnSizeOne() {
//        Given - add product to cart
        val cartProduct = CartProduct(restaurantId = "1",
                restaurantName = "Dominoz4",
                url = "",
                averageCostForTwo = 600,
                thumb = "",
                quantity = 3)

        cartViewModel.addProductToCart(cartProduct)

//          When - get all the products
        val cartProducts = cartViewModel.cartProductsLiveData

//        Then - the size should be 1
        assertThat(1, `is`(cartProducts.value!!.size))
    }

    @Test
    fun removeTask_AddProduct_returnSizeZero() {
//        Give - Add product to cart
        val cartProduct = CartProduct(restaurantId = "1",
                restaurantName = "Dominoz4",
                url = "",
                averageCostForTwo = 600,
                thumb = "",
                quantity = 3)

        cartViewModel.addProductToCart(cartProduct)

//        When - remove the product from cart

        cartViewModel.removeCartProduct(cartProduct)

//        Then - the cart should be empty
        val cartProducts = cartViewModel.cartProductsLiveData
        assertThat(0, `is`(cartProducts.value!!.size))
    }

    @Test
    fun deleteAllProducts_AddProducts_returnSizeZero() {
        //        Give - Add product to cart
        val cartProduct = CartProduct(restaurantId = "1",
                restaurantName = "Dominoz4",
                url = "",
                averageCostForTwo = 600,
                thumb = "",
                quantity = 3)

        cartViewModel.addProductToCart(cartProduct)
        val cartProduct2 = CartProduct(restaurantId = "2",
                restaurantName = "Dominoz4",
                url = "",
                averageCostForTwo = 800,
                thumb = "",
                quantity = 3)

        cartViewModel.addProductToCart(cartProduct2)

//        When - Delete all the products from cart
        cartViewModel.clearCart()


        //        Then - the cart should be empty
        val cartProducts = cartViewModel.cartProductsLiveData
        assertThat(0, `is`(cartProducts.value!!.size))
    }
}