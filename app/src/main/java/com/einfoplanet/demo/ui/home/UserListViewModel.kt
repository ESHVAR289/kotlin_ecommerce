package com.einfoplanet.demo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDao
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.domain.booklist.UserListUseCase
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val userListUseCase: UserListUseCase,
                                            private val appExecutors: AppExecutors,
                                            private val bookDao: BookDao) : ViewModel() {
    init {
        appExecutors.diskIO().execute {
            val author1 = BookAuthor("1", "Robert kiyosaki")
            bookDao.insertAuthor(author1)
            val author2 = BookAuthor("2", "Robin Sharma")
            bookDao.insertAuthor(author2)
            val author3 = BookAuthor("3", "Paulo kolheo")
            bookDao.insertAuthor(author3)
            val author4 = BookAuthor("4", "Elbert Einstein")
            bookDao.insertAuthor(author4)
        }
    }

    val books: LiveData<List<BookDetail>> = userListUseCase.getBooks()

    fun getBooks() {
        userListUseCase.getBooks()
    }

    fun insertBookData(bookDetail: BookDetail) {
        appExecutors.diskIO().execute {
            bookDao.insertBookDetail(bookDetail)
        }
    }

}