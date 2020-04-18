package com.einfoplanet.demo.di.module

import com.einfoplanet.demo.repository.UserListRepositoryImpl
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.domain.userlist.UsersListRepository
import dagger.Binds
import dagger.Module

@Module
interface UserDomainModule {
    @Binds
    @FragmentScope
    fun bindUserListRepository(userListRepository: UserListRepositoryImpl): UsersListRepository
}