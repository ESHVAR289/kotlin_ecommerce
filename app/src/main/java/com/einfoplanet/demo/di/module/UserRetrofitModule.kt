package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.network.RandomUserService
import com.einfoplanet.demo.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class UserRetrofitModule {

    @Provides
    @ApplicationScope
    fun provideRandomUserService(retrofit: Retrofit): RandomUserService =
            retrofit.create(RandomUserService::class.java)

    @Provides
    @ApplicationScope
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =

            Retrofit.Builder()
                    .baseUrl("https://randomuser.me/")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @ApplicationScope
    fun provideOkHttpCLient(interceptor: Interceptor): OkHttpClient =
            OkHttpClient()
                    .newBuilder()
                    .addInterceptor(interceptor)
                    .build()

    @Provides
    @ApplicationScope
    fun provideInterceptor() : Interceptor =  Interceptor { chain ->
        val request = chain.request()
        val url = request.url().newBuilder()
                .build()
        val newRequest = request.newBuilder()
                .url(url)
                .build()
        chain.proceed(newRequest)
    }
}

