package com.einfoplanet.demo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDao
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.db.BookImagesData
import com.einfoplanet.demo.domain.booklist.BookListUseCase
import javax.inject.Inject

class BookListViewModel @Inject constructor(private val bookListUseCase: BookListUseCase,
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

    val imagesLiveData: MutableLiveData<List<BookImagesData>> by lazy { MutableLiveData<List<BookImagesData>>() }
    val books: LiveData<List<BookDetail>> = bookListUseCase.getBooks()

    fun getBooks() {
        bookListUseCase.getBooks()
    }

    fun getImagesData(bookId: String) {
        appExecutors.diskIO().execute {
            imagesLiveData.postValue(bookDao.getSelectedBookImages(bookId))
        }
    }

    fun insertBookData(bookDetail: BookDetail) {
        appExecutors.diskIO().execute {
            bookDao.insertBookDetail(bookDetail)
        }
    }

    fun insertBooksImages(selectedFiles: List<BookImagesData>) {
        appExecutors.diskIO().execute {
            selectedFiles.forEach { bookImageData ->
                bookDao.insetBookImagesData(bookImageData)
            }
        }

    }

}