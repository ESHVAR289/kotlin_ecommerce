package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.repository.BooksListRepositoryImpl
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.domain.booklist.UsersListRepository
import dagger.Binds
import dagger.Module

@Module
interface BookDomainModule {
    @Binds
    @FragmentScope
    fun bindUserListRepository(userListRepository: BooksListRepositoryImpl): UsersListRepository
}