package com.einfoplanet.demo.domain.userlist

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.model.User
import com.einfoplanet.demo.domain.BoundaryState
import com.einfoplanet.demo.repository.LoadingStatus

// As we need to interact with repostiory which is in data layer. Implementing Dependency Inversion
// Principle we create an interface which concrete respository class in data layer implements.
// This way we remove any dependency on data layer from domain layer
interface UsersListRepository {
//    fun getUsers(): LiveData<PagedList<User>>
//    fun getBoundaryState(): LiveData<BoundaryState<Int>>
//    fun fetchMore(pageNo: Int, predicate: (String?) -> (Boolean)): LiveData<LoadingStatus>
//    fun returnLoadingOrSuccess(): LiveData<LoadingStatus>
//    fun refresh()
    fun getBooks(): LiveData<List<BookDetail>>
    fun getAuthors(): List<BookAuthor>
}