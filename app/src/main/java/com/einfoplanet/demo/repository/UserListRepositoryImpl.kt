package com.einfoplanet.demo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.einfoplanet.demo.AppExecutors
import com.einfoplanet.demo.db.UserData
import com.einfoplanet.demo.model.UserDataApiResponse
import com.einfoplanet.demo.model.User
import com.einfoplanet.demo.domain.userlist.UsersListRepository
import com.einfoplanet.demo.domain.BoundaryState
import timber.log.Timber
import javax.inject.Inject


class UserListRepositoryImpl @Inject constructor(
        private val appExecutors: AppExecutors,
        private val localData: UserLocalData,
        private val remoteData: UserRemoteData
) : UsersListRepository {
    private val loadingStatus = MutableLiveData<LoadingStatus>()

    override fun getUsers(): LiveData<PagedList<User>> {
        return localData.getUsers()
    }

    override fun getBoundaryState(): LiveData<BoundaryState<Int>> {
        return localData.getBoundaryState()
    }

    override fun refresh() {
        localData.refresh()
    }

    override fun fetchMore(pageNo: Int, predicate: (String?) -> (Boolean)): LiveData<LoadingStatus> {
        if (loadingStatus.value == null || loadingStatus.value?.status != Status.LOADING) {
            loadingStatus.value = LoadingStatus.loading()
            Timber.d("fetchMore starting: %s", pageNo)
            remoteData.fetchRandomUsers(pageNo, { users ->
                appExecutors.diskIO().execute {
                    localData.insertUsers(getTransformedUsers(users))
                    Timber.d("fetchMore saved: %s", pageNo)
                }
            }, { status ->
                loadingStatus.value = status
            })
        } else {
            Timber.d("fetchMore already loading %s", pageNo)
        }
        return loadingStatus
    }

    override fun returnLoadingOrSuccess(): LiveData<LoadingStatus> {
        if (loadingStatus.value == null || loadingStatus.value?.status != Status.LOADING) {
            loadingStatus.value = LoadingStatus.success()
        }
        return loadingStatus
    }

    fun getTransformedUsers(remoteUsersData: UserDataApiResponse): List<UserData> {
        return remoteUsersData.results.map {
            UserData(
                    title = it.name.title,
                    first = it.name.first,
                    last = it.name.last,
                    city = it.location.city,
                    state = it.location.state,
                    country = it.location.country,
                    latitude = it.location.coordinates.latitude,
                    longitude = it.location.coordinates.longitude,
                    date = it.dob.date,
                    age = it.dob.age,
                    large = it.picture.large,
                    medium = it.picture.medium,
                    thumbnail = it.picture.thumbnail,
                    id = it.location.postcode.toString(),
                    page = remoteUsersData.info.page,
                    seed = remoteUsersData.info.seed,
                    gender = it.gender)
        }
    }
}
