package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.repository.UserLocalData
import javax.inject.Inject

class UserLocalDataImpl @Inject constructor(private val appExecutors: AppExecutors,
                                            private val userDao: UserDao,
                                            private val bookDao: BookDao) :
        UserLocalData {
    override fun getBooks(): LiveData<List<BookDetail>> {
        return bookDao.getAllSavedBooks()
    }

    override fun getAuthors(): List<BookAuthor> {
        return bookDao.getAuthors()
    }
}