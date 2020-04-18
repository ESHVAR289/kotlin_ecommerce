package com.einfoplanet.demo.di.component

import com.einfoplanet.demo.di.module.UserDataModule
import com.einfoplanet.demo.di.module.UserDomainModule
import com.einfoplanet.demo.di.scope.FragmentScope
import com.einfoplanet.demo.ui.home.UsersFragment
import com.einfoplanet.demo.ui.home.UsersListFragment
import dagger.Subcomponent

@Subcomponent(modules = [UserDataModule::class, UserDomainModule::class])
@FragmentScope
interface FragmentComponent {
    fun inject(usersListFragment: UsersListFragment)
    fun inject(usersFragment: UsersFragment)
}