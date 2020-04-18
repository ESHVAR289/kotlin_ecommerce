package com.einfoplanet.demo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.UserDao
import com.einfoplanet.demo.domain.userlist.UserListUseCase
import com.einfoplanet.demo.domain.Direction
import com.einfoplanet.demo.repository.LoadingStatus
import com.einfoplanet.demo.listeners.AcceptDeclineButtonClickListener
import com.einfoplanet.demo.model.User
import timber.log.Timber
import javax.inject.Inject

class UserListViewModel @Inject constructor(private val userListUseCase: UserListUseCase,
                                            private val appExecutors: AppExecutors,
                                            private val userDao: UserDao) : ViewModel(), AcceptDeclineButtonClickListener {
    //Live data which is holding the users data from DB
    private val _acceptedUsersLiveDataFromDB: MutableLiveData<List<User>> by lazy { MutableLiveData<List<User>>() }
    val usersLiveDataFromDB: LiveData<List<User>>
        get() = _acceptedUsersLiveDataFromDB

    //Live data which is holding the users data from DB
    private val _clickedUsr: MutableLiveData<User> by lazy { MutableLiveData<User>() }
    val clickedUser: LiveData<User>
        get() = _clickedUsr

    val users = userListUseCase.getUsers()

    //PagedList use BoundaryCallback object to send us callback about necessary events related to
    // data loading. Here we capture those events and fetch data from the network. The
    // userListUseCase.fetchMore() function returns loading status which we observe in UI to
    // show progress bar.
    val loadingStatus: LiveData<LoadingStatus> = Transformations.switchMap(
            userListUseCase.getBoundaryState()) { onBoundaryItemLoaded(it.itemData, it.direction) }

    private fun onBoundaryItemLoaded(pageNo: Int, direction: Direction): LiveData<LoadingStatus> {
        Timber.d("onBoundaryItemLoaded %s %s ", pageNo, direction)
        return userListUseCase.fetchMore(pageNo, direction)
    }

    fun refresh() {
        Timber.d("refreshing")
        userListUseCase.refresh()
    }

    override fun onButtonClick(selectedUser: User, flag: Int) {
        appExecutors.diskIO().execute {
            userDao.updateAcceptDeclineStatus(selectedUser.id, flag)
        }
    }

    override fun onCardSelected(selectedUser: User) {
        _clickedUsr.value = selectedUser
    }

    fun getAcceptedDeclinedUsers(flagAcceptedOrDeclined: Int) {
        appExecutors.diskIO().execute {
            _acceptedUsersLiveDataFromDB.postValue(userDao.getAcceptedOrDeclinedUsers(flagAcceptedOrDeclined))
        }
    }
}