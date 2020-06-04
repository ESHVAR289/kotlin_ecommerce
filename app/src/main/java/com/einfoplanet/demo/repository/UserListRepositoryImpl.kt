package com.einfoplanet.demo.repository

import androidx.lifecycle.LiveData
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.domain.userlist.UsersListRepository
import javax.inject.Inject


class UserListRepositoryImpl @Inject constructor(
        private val appExecutors: AppExecutors,
        private val localData: UserLocalData
) : UsersListRepository {

    override fun getBooks(): LiveData<List<BookDetail>> {
        return localData.getBooks()
    }

    override fun getAuthors(): List<BookAuthor> {
        return localData.getAuthors()
    }
}
