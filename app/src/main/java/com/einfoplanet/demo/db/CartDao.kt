package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM CARTPRODUCT")
    fun getAllCartProducts(): LiveData<List<CartProduct>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProduct(product: CartProduct): Long

    @Query("SELECT COUNT(*) FROM CARTPRODUCT")
    fun getCartProductCount(): LiveData<Int>

    @Query("SELECT SUM(quantity * averageCostForTwo) FROM CARTPRODUCT")
    fun getAllProductTotalPrice(): LiveData<Int>

    @Query("DELETE FROM CARTPRODUCT where restaurantId= :restaurantId")
    fun deleteProductFromCart(restaurantId: String): Int

    @Query("DELETE FROM cartproduct")
    fun clearCart()
}