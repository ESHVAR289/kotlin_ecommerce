package com.einfoplanet.demo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
        entities = [
            BookDetail::class,
            BookAuthor::class,
            BookImagesData::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(DbTypeConverters::class)
abstract class BookDb : RoomDatabase() {
    abstract fun bookDao(): BookDao
}