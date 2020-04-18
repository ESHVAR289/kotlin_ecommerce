package com.einfoplanet.demo.repository

import com.einfoplanet.demo.model.UserDataApiResponse

// This interface is in accordance to Dependency Inversion Principle by separating the higher
// repository class from lower network class.
interface UserRemoteData {
    fun fetchRandomUsers(pageNo: Int,
                         onSuccess: (users: UserDataApiResponse) -> Unit,
                         onStatus: (status: LoadingStatus) -> Unit)
}