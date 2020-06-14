package com.einfoplanet.demo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CartProduct(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @SerializedName("restaurant_id") val restaurantId: String,
        @SerializedName("restaurant_name") val restaurantName: String,
        @SerializedName("url") var url: String,
        @SerializedName("average_cost_for_two") var averageCostForTwo: Int,
        @SerializedName("thumb") var thumb: String,
        @SerializedName("quantity") var quantity: Int
)