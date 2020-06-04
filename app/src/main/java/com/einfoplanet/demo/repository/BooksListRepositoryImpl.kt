package com.einfoplanet.demo.repository

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.domain.booklist.UsersListRepository
import javax.inject.Inject


class BooksListRepositoryImpl @Inject constructor(
        private val appExecutors: AppExecutors,
        private val localData: BooksLocalData
) : UsersListRepository {

    override fun getBooks(): LiveData<List<BookDetail>> {
        return localData.getBooks()
    }

    override fun getAuthors(): List<BookAuthor> {
        return localData.getAuthors()
    }
}
