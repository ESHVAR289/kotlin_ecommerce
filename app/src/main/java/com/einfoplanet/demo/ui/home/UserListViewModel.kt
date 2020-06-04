package com.einfoplanet.demo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.BookAuthor
import com.einfoplanet.demo.db.BookDao
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.domain.userlist.UserListUseCase
import com.einfoplanet.demo.listeners.AcceptDeclineButtonClickListener
import com.einfoplanet.demo.model.User
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val userListUseCase: UserListUseCase,
                                            private val appExecutors: AppExecutors,
                                            private val bookDao: BookDao) : ViewModel(), AcceptDeclineButtonClickListener {
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

    //Live data which is holding the users data from DB
//    private val _acceptedUsersLiveDataFromDB: MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
//    val usersLiveDataFromDB: LiveData<List<User>>
//        get() = _acceptedUsersLiveDataFromDB
//
//    //Live data which is holding the users data from DB
    private val _strDateSelected: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val strDateSelected: LiveData<String>
        get() = _strDateSelected

//    val users = userListUseCase.getUsers()

    val books: LiveData<List<BookDetail>> = userListUseCase.getBooks()

    //PagedList use BoundaryCallback object to send us callback about necessary events related to
    // data loading. Here we capture those events and fetch data from the network. The
    // userListUseCase.fetchMore() function returns loading status which we observe in UI to
    // show progress bar.
//    val loadingStatus: LiveData<LoadingStatus> = Transformations.switchMap(
//            userListUseCase.getBoundaryState()) { onBoundaryItemLoaded(it.itemData, it.direction) }

//    private fun onBoundaryItemLoaded(pageNo: Int, direction: Direction): LiveData<LoadingStatus> {
//        Timber.d("onBoundaryItemLoaded %s %s ", pageNo, direction)
//        return userListUseCase.fetchMore(pageNo, direction)
//    }
//
//    fun refresh() {
//        Timber.d("refreshing")
//        userListUseCase.refresh()
//    }

    fun getBooks() {
        userListUseCase.getBooks()
    }

    fun insertBookData(bookDetail: BookDetail) {
        appExecutors.diskIO().execute {
            bookDao.insertBookDetail(bookDetail)
        }
    }

    fun getAuthors() {
        userListUseCase.getAuthors()
    }

    override fun onButtonClick(selectedUser: User, flag: Int) {
//        appExecutors.diskIO().execute {
//            userDao.updateAcceptDeclineStatus(selectedUser.id, flag)
//        }
    }

    override fun onCardSelected(selectedUser: User) {
//        _clickedUsr.value = selectedUser
    }

    fun getAcceptedDeclinedUsers(flagAcceptedOrDeclined: Int) {
//        appExecutors.diskIO().execute {
//            _acceptedUsersLiveDataFromDB.postValue(userDao.getAcceptedOrDeclinedUsers(flagAcceptedOrDeclined))
//        }
    }
}