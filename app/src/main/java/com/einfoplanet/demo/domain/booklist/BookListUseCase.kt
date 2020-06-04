package com.einfoplanet.demo.domain.booklist

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDetail
import javax.inject.Inject

class BookListUseCase @Inject constructor(private val repository: UsersListRepository) {

    fun getBooks(): LiveData<List<BookDetail>> {
        return repository.getBooks()
    }

    fun getAuthors(): List<BookAuthor> {
        return repository.getAuthors()
    }
}