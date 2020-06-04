package com.einfoplanet.demo.domain.userlist

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.domain.BoundaryState
import com.einfoplanet.demo.domain.Direction
import com.einfoplanet.demo.domain.Logger
import com.einfoplanet.demo.model.User
import com.einfoplanet.demo.repository.LoadingStatus
import javax.inject.Inject

class UserListUseCase @Inject constructor(private val repository: UsersListRepository) {

    fun getBooks(): LiveData<List<BookDetail>> {
        return repository.getBooks()
    }

    fun getAuthors(): List<BookAuthor> {
        return repository.getAuthors()
    }
}