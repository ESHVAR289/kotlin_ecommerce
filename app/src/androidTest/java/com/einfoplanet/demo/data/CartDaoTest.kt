/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.einfoplanet.demo.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.db.ShoppingDb
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CartDaoTest {

    private lateinit var database: ShoppingDb

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                ShoppingDb::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertProductAndGetById() {
        // GIVEN - insert a task
        val product = CartProduct(restaurantId = "12134",
                restaurantName = "Dominoz",
                url = "",
                averageCostForTwo = 500,
                thumb = "",
                quantity = 5)
        database.cartDao().insertProduct(product)
        // WHEN - Get the task by restaurantId from the database
        val loaded = database.cartDao().getProductFromCart(product.restaurantId)

        // THEN - The loaded data contains the expected values
        assertThat(loaded, notNullValue())
        assertThat(loaded.restaurantId, `is`(product.restaurantId))
        assertThat(loaded.restaurantName, `is`(product.restaurantName))
        assertThat(loaded.url, `is`(product.url))
        assertThat(loaded.averageCostForTwo, `is`(product.averageCostForTwo))
        assertThat(loaded.thumb, `is`(product.thumb))
        assertThat(loaded.quantity, `is`(product.quantity))
    }

    @Test
    fun clearCart() {
        // GIVEN - insert a task
        val product = CartProduct(restaurantId = "12134",
                restaurantName = "Dominoz",
                url = "",
                averageCostForTwo = 500,
                thumb = "",
                quantity = 5)
        database.cartDao().insertProduct(product)

        // When - delete all the products
        database.cartDao().clearCart()


        // THEN - The loaded data contains the expected values
        val count = database.cartDao().getCartProductCount()
        assertThat(null, `is`(count.value))
    }
}
