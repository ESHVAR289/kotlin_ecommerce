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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: CartProduct)

    @Query("SELECT COUNT(*) FROM CARTPRODUCT")
    fun getCartProductCount(): Int

    @Query("SELECT SUM(quantity * averageCostForTwo) FROM CARTPRODUCT")
    fun getAllProductTotalPrice(): Int
}