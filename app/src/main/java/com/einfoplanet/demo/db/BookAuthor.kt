package com.einfoplanet.demo.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "authors")
data class BookAuthor(
        @PrimaryKey var id: String,
        @SerializedName("author_name") var authorName: String
)