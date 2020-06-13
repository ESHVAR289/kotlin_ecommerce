package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.BuildConfig
import com.einfoplanet.demo.di.scope.ApplicationScope
import com.einfoplanet.demo.network.RestaurantService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RestaurantRetrofitModule {

    @Provides
    @ApplicationScope
    fun provideRandomUserService(retrofit: Retrofit): RestaurantService =
            retrofit.create(RestaurantService::class.java)

    @Provides
    @ApplicationScope
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =

            Retrofit.Builder()
                    .baseUrl("https://developers.zomato.com/api/v2.1/")
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
    fun provideInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url().newBuilder()
                .build()
        val newRequest = request.newBuilder()
                .header("user-key", BuildConfig.ZOMATO_API_KEY)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .url(url)
                .build()
        chain.proceed(newRequest)
    }
}

