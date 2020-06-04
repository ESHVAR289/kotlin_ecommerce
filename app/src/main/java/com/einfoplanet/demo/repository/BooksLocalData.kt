package com.einfoplanet.demo.repository

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDetail

// This interface is in accordance to Dependency Inversion Principle by separating the higher
// repository class from lower database class.
interface BooksLocalData {
    fun getBooks(): LiveData<List<BookDetail>>
    fun getAuthors(): List<BookAuthor>
}