package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.repository.CartListRepositoryImpl
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.domain.cartlist.CartListRepository
import dagger.Binds
import dagger.Module

@Module
interface CartModule {
    @Binds
    @FragmentScope
    fun bindCartProductListRepository(userListRepository: CartListRepositoryImpl): CartListRepository
}