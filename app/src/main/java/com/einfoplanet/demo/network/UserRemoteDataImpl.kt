package com.einfoplanet.demo.network

import com.einfoplanet.demo.model.UserDataApiResponse
import com.einfoplanet.demo.repository.UserRemoteData
import com.einfoplanet.demo.repository.ErrorCode
import com.einfoplanet.demo.repository.LoadingStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class UserRemoteDataImpl @Inject constructor(private val randomUserService: RandomUserService) :
        UserRemoteData {
    override fun fetchRandomUsers(pageNo: Int,
                                  onSuccess: (users: UserDataApiResponse) -> Unit,
                                  onStatus: (status: LoadingStatus) -> Unit) {
        val call = randomUserService.getRandomUser(pageNo, "50", "abc")
        call.enqueue(object : Callback<UserDataApiResponse> {
            override fun onResponse(call: Call<UserDataApiResponse>?, response: Response<UserDataApiResponse>?) {
                if (response != null) {
                    if (response.body() == null) {
                        onStatus(LoadingStatus.error(ErrorCode.NO_DATA))
                    } else {
                        response.body()?.let {
                            onSuccess(it)
                            Timber.d("fetchMore saved: %s", pageNo)
                        }
                        onStatus(LoadingStatus.success())
                    }
                }
            }

            override fun onFailure(call: Call<UserDataApiResponse>?, t: Throwable?) {
                if (t is IOException) {
                    onStatus(
                            LoadingStatus.error(
                                    ErrorCode.NETWORK_ERROR, t.message))
                } else {
                    onStatus(
                            LoadingStatus.error(
                                    ErrorCode.UNKNOWN_ERROR, null))
                }
            }
        })
    }
}