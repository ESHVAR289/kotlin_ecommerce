package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.db.CartLocalData
import com.einfoplanet.demo.db.CartLocalDataImpl
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.domain.restaurantlist.RestaurantListRepository
import com.einfoplanet.demo.network.RestaurantRemoteDataImpl
import com.einfoplanet.demo.network.UserRemoteDataImpl
import com.einfoplanet.demo.repository.UserRemoteData
import dagger.Binds
import dagger.Module

@Module
interface RestaurantDataModule {

    @Binds
    @FragmentScope
    fun bindUserLocalData(cartLocalDataImpl: CartLocalDataImpl): CartLocalData

    @Binds
    @FragmentScope
    fun bindUserRemoteData(userRemoteData: UserRemoteDataImpl): UserRemoteData

    @Binds
    @FragmentScope
    fun bindRestaurantRemoteData(restaurantRemoteDataImpl: RestaurantRemoteDataImpl): RestaurantListRepository
}