package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.repository.BooksLocalData
import javax.inject.Inject

class BookLocalDataImpl @Inject constructor(private val appExecutors: AppExecutors,
                                            private val bookDao: BookDao) :
        BooksLocalData {
    override fun getBooks(): LiveData<List<BookDetail>> {
        return bookDao.getAllSavedBooks()
    }

    override fun getAuthors(): List<BookAuthor> {
        return bookDao.getAuthors()
    }
}