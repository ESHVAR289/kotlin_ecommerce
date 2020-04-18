package com.einfoplanet.demo.network

import com.einfoplanet.demo.model.UserDataApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RandomUserService {
    @Headers("Accept: application/json")
    @GET("api/")
    fun getRandomUser(@Query("page") pageNo: Int,
                      @Query("results") results: String,
                      @Query("seed") seed: String):
            Call<UserDataApiResponse>
}
