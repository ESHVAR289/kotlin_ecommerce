package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.db.BookLocalDataImpl
import com.einfoplanet.demo.network.UserRemoteDataImpl
import com.einfoplanet.demo.repository.BooksLocalData
import com.einfoplanet.demo.repository.UserRemoteData
import com.einfoplanet.demo.di.scope.FragmentScope
import dagger.Binds
import dagger.Module

@Module
interface BookDataModule {

    @Binds
    @FragmentScope
    fun bindUserLocalData(bookLocalData: BookLocalDataImpl): BooksLocalData

    @Binds
    @FragmentScope
    fun bindUserRemoteData(userRemoteData: UserRemoteDataImpl): UserRemoteData

}