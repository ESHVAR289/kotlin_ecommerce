package com.einfoplanet.demo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "book_detail")
data class BookDetail(
        @PrimaryKey var id: String,
        @SerializedName("book_name") var bookName: String,
        @SerializedName("author_name") var authorName: String,
        @SerializedName("price") var price: String,
        @SerializedName("date_of_issue") var dateOfIssue: String,
        @SerializedName("cover_image_path") var coverImgPath: String
)