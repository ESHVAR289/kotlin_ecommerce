package com.einfoplanet.demo.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.db.UserData
import com.einfoplanet.demo.model.User
import com.einfoplanet.demo.domain.BoundaryState

// This interface is in accordance to Dependency Inversion Principle by separating the higher
// repository class from lower database class.
interface UserLocalData {
//    fun getUsers(): LiveData<PagedList<User>>
//    fun getBoundaryState(): LiveData<BoundaryState<Int>>
//    fun insertUsers(users: List<UserData>)
//    fun deleteUsers()
//    fun refresh()
    fun getBooks(): LiveData<List<BookDetail>>
    fun getAuthors(): List<BookAuthor>
}