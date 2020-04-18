package com.einfoplanet.demo.domain.userlist

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.einfoplanet.demo.domain.Logger
import com.einfoplanet.demo.model.User
import com.einfoplanet.demo.domain.BoundaryState
import com.einfoplanet.demo.domain.Direction
import com.einfoplanet.demo.repository.LoadingStatus
import javax.inject.Inject

class UserListUseCase @Inject constructor(private val repository: UsersListRepository, private val log: Logger) {

    fun getUsers(): LiveData<PagedList<User>> {
        return repository.getUsers()
    }

    fun getBoundaryState(): LiveData<BoundaryState<Int>> {
        return repository.getBoundaryState()
    }

    // Check which direction the event happened. If the user has scrolled to the top, the
    // direction will be TOP and if user has scrolled to the bottom (no more data in database)
    // then direction will be BOTTOM. If there is no data (usually first time the app start)
    // then fetch users for current date
    fun fetchMore(pageNo: Int, direction: Direction): LiveData<LoadingStatus> {
        return repository.fetchMore(pageNo + 1) { posterPath ->
            posterPath != null
        }
    }

    fun refresh() {
        repository.refresh()
    }
}