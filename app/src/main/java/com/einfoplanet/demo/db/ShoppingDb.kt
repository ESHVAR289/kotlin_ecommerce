package com.einfoplanet.demo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
        entities = [
            CartProduct::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(DbTypeConverters::class)
abstract class ShoppingDb : RoomDatabase() {
    abstract fun cartDao(): CartDao
}