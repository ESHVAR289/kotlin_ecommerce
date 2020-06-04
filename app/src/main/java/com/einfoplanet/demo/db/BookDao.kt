package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {
    @Query("SELECT * FROM book_detail")
    fun getAllSavedBooks() : LiveData<List<BookDetail>>

    @Query("SELECT * FROM books_img_data where bookId= :bookId")
    fun getSelectedBookImages(bookId: String): List<BookImagesData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookDetail(bookData: BookDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetBookImagesData(bookImagesData: BookImagesData)

    @Query("SELECT * FROM authors")
    fun getAuthors(): List<BookAuthor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthor(author: BookAuthor)
}