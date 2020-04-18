package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.db.UserLocalDataImpl
import com.einfoplanet.demo.network.UserRemoteDataImpl
import com.einfoplanet.demo.repository.UserLocalData
import com.einfoplanet.demo.repository.UserRemoteData
import com.einfoplanet.demo.di.scope.FragmentScope
import dagger.Binds
import dagger.Module

@Module
interface UserDataModule {

    @Binds
    @FragmentScope
    fun bindUserLocalData(userLocalData: UserLocalDataImpl): UserLocalData

    @Binds
    @FragmentScope
    fun bindUserRemoteData(userRemoteData: UserRemoteDataImpl): UserRemoteData

}