package com.einfoplanet.demo.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.einfoplanet.demo.model.User
import com.einfoplanet.demo.domain.BoundaryState
import timber.log.Timber

class UserBoundaryCallback : PagedList.BoundaryCallback<User>() {

    private val _boundaryState = MutableLiveData<BoundaryState<Int>>()
    val boundaryState: LiveData<BoundaryState<Int>>
        get() = _boundaryState

    companion object {
        const val DATABASE_PAGE_SIZE = 10
    }

    override fun onZeroItemsLoaded() {
        _boundaryState.value = BoundaryState.itemLoadedAtBottom(0)
    }

    override fun onItemAtEndLoaded(itemAtEnd: User) {
        _boundaryState.value = BoundaryState.itemLoadedAtBottom(itemAtEnd.page)
    }

    fun refresh() {
        Timber.d("refresh")
        _boundaryState.value = BoundaryState.zeroItemsLoaded(0)
    }

}