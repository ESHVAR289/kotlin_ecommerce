package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.repository.UserLocalData
import com.einfoplanet.demo.model.User
import com.einfoplanet.demo.domain.BoundaryState
import javax.inject.Inject

class UserLocalDataImpl @Inject constructor(private val appExecutors: AppExecutors,
                                            private val userDao: UserDao) :
        UserLocalData {
    private val boundaryCallback = UserBoundaryCallback()

    override fun getUsers(): LiveData<PagedList<User>> {
        val dataSourceFactory = userDao.getDataSourcefactory()
        return LivePagedListBuilder(dataSourceFactory, UserBoundaryCallback.DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
    }

    override fun getBoundaryState(): LiveData<BoundaryState<Int>> {
        return boundaryCallback.boundaryState
    }

    override fun insertUsers(users: List<UserData>) {
        appExecutors.diskIO().execute {
            userDao.insertUsers(users)
        }
    }

    override fun deleteUsers() {
        userDao.deleteUsers()
    }

    override fun refresh() {
        boundaryCallback.refresh()
    }
}