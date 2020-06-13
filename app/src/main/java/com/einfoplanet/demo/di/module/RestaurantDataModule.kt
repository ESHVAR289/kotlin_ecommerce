package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.db.BookLocalDataImpl
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.domain.restaurantlist.RestaurantListRepository
import com.einfoplanet.demo.network.RestaurantRemoteDataImpl
import com.einfoplanet.demo.network.UserRemoteDataImpl
import com.einfoplanet.demo.repository.BooksLocalData
import com.einfoplanet.demo.repository.RestaurantRemoteData
import com.einfoplanet.demo.repository.UserRemoteData
import dagger.Binds
import dagger.Module

@Module
interface RestaurantDataModule {

    @Binds
    @FragmentScope
    fun bindUserLocalData(bookLocalData: BookLocalDataImpl): BooksLocalData

    @Binds
    @FragmentScope
    fun bindUserRemoteData(userRemoteData: UserRemoteDataImpl): UserRemoteData

    @Binds
    @FragmentScope
    fun bindRestaurantRemoteData(restaurantRemoteDataImpl: RestaurantRemoteDataImpl): RestaurantListRepository
}