package com.einfoplanet.demo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "books_img_data")
data class BookImagesData(
        @PrimaryKey var id: String,
        @SerializedName("book_id") var bookId: String,
        @SerializedName("path") var imgPath: String
)